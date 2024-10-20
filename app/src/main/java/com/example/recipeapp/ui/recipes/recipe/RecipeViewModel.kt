package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.Constants.PREF_FILE_NAME
import com.example.recipeapp.data.FavoritesLocalDataSources
import com.example.recipeapp.data.FavoritesRepository
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import java.io.InputStream

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private val recipeRepository: RecipeRepository =
        RecipeRepository()

    private val favoritesRepository: FavoritesRepository =
        FavoritesRepository(
            favoritesLocalDataSources = FavoritesLocalDataSources(
                (application.getSharedPreferences(
                    PREF_FILE_NAME, MODE_PRIVATE
                ))
            )
        )

    private val _recipeState = MutableLiveData(RecipeUiState())
    private val recipeState: LiveData<RecipeUiState> get() = _recipeState

    fun getRecipeState(recipeId: Int): LiveData<RecipeUiState> {
        loadRecipe(recipeId)
        return recipeState
    }

    fun loadRecipe(recipeId: Int) {
        //TODO `load from network`
        val isFavorite = favoritesRepository.checkIsFavorites(recipeId)

        recipeRepository.getRecipeById(recipeId) { getRecipe ->
            var drawable: Drawable? = null
            try {
                val inputStream: InputStream? =
                    application.assets?.open("${getRecipe?.imageUrl}")
                drawable = Drawable.createFromStream(inputStream, null)
            } catch (e: Exception) {
                Log.e("!!!", e.stackTrace.toString())
            }
            _recipeState.postValue(
                recipeState.value?.copy(
                    recipe = getRecipe,
                    isFavorites = isFavorite,
                    recipeImage = drawable
                )
            )
        }
    }

    fun onFavoritesClicked(recipeId: Int?) {
        fun isFavorites() = favoritesRepository.checkIsFavorites(recipeId)
        val favoriteSet = favoritesRepository.getRecipeData()
        val newSet = if (isFavorites()) {
            favoriteSet.minus(recipeId.toString())
        } else {
            favoriteSet.plus(recipeId.toString())
        }
        favoritesRepository.setRecipeData(newSet)
        _recipeState.value = recipeState.value?.copy(isFavorites = isFavorites())
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