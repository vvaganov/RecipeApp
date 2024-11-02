package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private val recipeRepository = RecipeRepository(application)

    private val _recipeState = MutableLiveData(RecipeUiState())
    val recipeState: LiveData<RecipeUiState> get() = _recipeState

    fun loadRecipe(recipe: Recipe) {

        _recipeState.postValue(
            recipeState.value?.copy(
                isFavorites = recipe.isFavorites,
            )
        )
    }

    fun onFavoritesClicked(recipeId: Int) {

        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeByIdFromCash(recipeId)
            recipeRepository.updateRecipe(recipe.copy(isFavorites = !recipe.isFavorites))
            _recipeState.postValue(
                recipeState.value?.copy(
                    isFavorites = recipeRepository.getRecipeByIdFromCash(recipeId).isFavorites
                )
            )
        }
    }

    fun changeNumberOfServing(progress: Int) {
        _recipeState.value = recipeState.value?.copy(numberServings = progress)
    }

    data class RecipeUiState(
        val isFavorites: Boolean = false,
        val numberServings: Int = 1,
    )
}