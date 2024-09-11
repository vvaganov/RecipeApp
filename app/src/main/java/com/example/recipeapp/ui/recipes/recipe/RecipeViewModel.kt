package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipeState = MutableLiveData(RecipeUiState())
    val recipeState: LiveData<RecipeUiState?> get() = _recipeState

    private fun setRecipeUi(recipeUiState: RecipeUiState?) {
        _recipeState.value = recipeUiState
    }

    private val recipeUiState = RecipeUiState()

    init {
        Log.i("!!!", "workViewModel-${_recipeState.value?.isFavorites}")
        setRecipeUi(recipeUiState.copy(isFavorites = true))
    }

    data class RecipeUiState(

        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val numberServings: Int = 1
    )
}