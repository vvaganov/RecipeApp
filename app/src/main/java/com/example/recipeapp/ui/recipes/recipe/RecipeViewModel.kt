package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.PREF_FAVORITE_KEY
import com.example.recipeapp.PREF_FILE_NAME
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe
import java.io.InputStream

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private val repository: RecipeRepository = RecipeRepository(context = application)

    private val _recipeState = MutableLiveData(RecipeUiState())
    val recipeState: LiveData<RecipeUiState> get() = _recipeState

    fun loadRecipe(recipeId: Int?) {
        //TODO `load from network`
        val isFavorite = repository.getFavorites().toList().contains(recipeId.toString())
        val recipe = repository.getRecipeById(recipeId)
        var drawable: Drawable? = null

        try {
            val inputStream: InputStream? =
                application.assets?.open("${recipe?.imageUrl}")
            drawable = Drawable.createFromStream(inputStream, null)
        } catch (e: Exception) {
            Log.e("!!!", e.stackTrace.toString())
        }
        _recipeState.value = recipeState.value?.copy(
            recipe = recipe,
            isFavorites = isFavorite,
            recipeImage = drawable
        )
    }

    fun onFavoritesClicked(recipeId: Int?) {
        var isFavorites = repository.getFavorites().toList().contains(recipeId.toString())
        val favoriteSet = repository.getFavorites()
        val newSet = if (isFavorites) {
            favoriteSet.minus(recipeId.toString())
        } else {
            favoriteSet.plus(recipeId.toString())
        }
        repository.setFavorites(newSet)
        isFavorites = repository.getFavorites().toList().contains(recipeId.toString())
        _recipeState.value = recipeState.value?.copy(isFavorites = isFavorites)
    }

    fun changeNumberOfServing(progress: Int) {
        _recipeState.value = recipeState.value?.copy(numberServings = progress)
    }

    data class RecipeUiState(

        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val numberServings: Int = 1,
        val recipeImage: Drawable? = null
    )
}