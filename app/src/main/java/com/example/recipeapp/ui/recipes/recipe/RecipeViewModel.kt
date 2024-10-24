package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.Constants.PREF_FILE_NAME
import com.example.recipeapp.data.FavoritesLocalDataSources
import com.example.recipeapp.data.FavoritesRepository
import com.example.recipeapp.model.Recipe

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private val favoritesRepository: FavoritesRepository =
        FavoritesRepository(
            favoritesLocalDataSources = FavoritesLocalDataSources(
                (application.getSharedPreferences(
                    PREF_FILE_NAME, MODE_PRIVATE
                ))
            )
        )

    private val _recipeState = MutableLiveData(RecipeUiState())
    val recipeState: LiveData<RecipeUiState> get() = _recipeState

    fun loadRecipe(recipeId: Int) {

        val isFavorite = favoritesRepository.checkIsFavorites(recipeId)

        _recipeState.postValue(
            recipeState.value?.copy(
                isFavorites = isFavorite,
            )
        )
    }

    fun onFavoritesClicked(recipe: Recipe) {
        fun isFavorites() = favoritesRepository.checkIsFavorites(recipe.id)
        val favoriteSet = favoritesRepository.getRecipeData()
        val newSet = if (isFavorites()) {
            favoriteSet.minus(recipe.id.toString())
        } else {
            favoriteSet.plus(recipe.id.toString())
        }
        favoritesRepository.setRecipeData(newSet)
        _recipeState.value = recipeState.value?.copy(isFavorites = isFavorites())
    }

    fun changeNumberOfServing(progress: Int) {
        _recipeState.value = recipeState.value?.copy(numberServings = progress)
    }

    data class RecipeUiState(
        val isFavorites: Boolean = false,
        val numberServings: Int = 1,
    )
}