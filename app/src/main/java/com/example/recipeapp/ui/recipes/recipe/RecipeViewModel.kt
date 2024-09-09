package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    private val mutableSelectedRecipe = MutableLiveData<RecipeUiState?>()
    val selectedRecipe: MutableLiveData<RecipeUiState?> get() = mutableSelectedRecipe

    private fun setRecipeUi(recipeUiState: RecipeUiState?) {
        mutableSelectedRecipe.value = recipeUiState
    }

    private val recipeUiState = RecipeUiState()

    init {
        setRecipeUi(recipeUiState)
        Log.i("!!!", "workViewModel-${mutableSelectedRecipe.value?.isFavorites}")
        setRecipeUi(recipeUiState.copy(isFavorites = true))
    }

    data class RecipeUiState(

        val recipe: Recipe? = null,
        var isFavorites: Boolean = false,
        val numberServings: Int = 1
    )
}