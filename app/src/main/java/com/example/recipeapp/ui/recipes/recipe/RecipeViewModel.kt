package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.PREF_FAVORITE_KEY
import com.example.recipeapp.PREF_FILE_NAME
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe
import java.io.InputStream

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _recipeState = MutableLiveData(RecipeUiState())
    val recipeState: LiveData<RecipeUiState?> get() = _recipeState

    private fun setRecipeUi(recipeUiState: RecipeUiState?) {
        _recipeState.value = recipeUiState
    }

    fun loadRecipe(recipeId: Int?) {
        //TODO `load from network`
        val isFavorite = getFavorites().toList().contains(recipeId.toString())
        val recipe = STUB.getRecipeById(recipeId)
        if (isFavorite) {
            setRecipeUi(_recipeState.value?.copy(recipe = recipe, isFavorites = true))
        } else {
            setRecipeUi(_recipeState.value?.copy(recipe = recipe, isFavorites = false))
        }
        try {
            val inputStream: InputStream? =
                application.assets?.open("${recipe?.imageUrl}")
            val drawable = Drawable.createFromStream(inputStream, null)
            setRecipeUi(_recipeState.value?.copy(recipeImage = drawable))
        } catch (e: Exception) {
            Log.e("!!!", e.stackTrace.toString())
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPref = application.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val setId = sharedPref?.getStringSet(PREF_FAVORITE_KEY, emptySet())
        val mutableSteId = HashSet<String>(setId)
        return mutableSteId
    }

    private fun setFavorites(setFavoriteId: Set<String>?) {
        val sharedPref = application.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
            ?: return
        sharedPref.edit()?.putStringSet(PREF_FAVORITE_KEY, setFavoriteId)?.apply()
    }

    fun onFavoritesClicked(recipeId: Int?) {
        if (!getFavorites().toList().contains(recipeId.toString())) {
            val favoriteSet = getFavorites()
            val newSet = favoriteSet.plus(recipeId.toString())
            setFavorites(newSet)
            setRecipeUi(_recipeState.value?.copy(isFavorites = true))
        } else {
            val favoriteSet = getFavorites()
            val newSet = favoriteSet.minus(recipeId.toString())
            setFavorites(newSet)
            setRecipeUi(_recipeState.value?.copy(isFavorites = false))
        }
    }

    data class RecipeUiState(

        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val numberServings: Int = 1,
        val recipeImage: Drawable? = null
    )
}