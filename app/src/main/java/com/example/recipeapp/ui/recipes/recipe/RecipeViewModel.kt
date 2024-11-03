package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

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
            val recipe = repository.getRecipeByIdFromCash(recipeId)
            repository.updateRecipe(recipe.copy(isFavorites = !recipe.isFavorites))
            _recipeState.postValue(
                recipeState.value?.copy(
                    isFavorites = repository.getRecipeByIdFromCash(recipeId).isFavorites
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