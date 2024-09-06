package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    data class RecipeUiState(

        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val numberServings: String = "1"
    )
}