package com.example.recipeapp.ui.recipes.recipe

import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import com.example.recipeapp.R

data class RecipeUiState(

    val imageTitle: String? = null,
    val recipeTitle: Int? = null,
    val isFavorites: Int = R.drawable.ic_heart_empty,
    val ingredientsTitle: Int = R.string.title_ingredient,
    val servingTitle: Int = R.string.title_portions,
    val numberServings: String = "1",
    val seekBar: SeekBar? = null,
    val ingredientsList: List<Ingredient> = emptyList(),
    val cookingMethod: Int = R.string.cooking_method,
    val methodList: List<String> = emptyList(),

)

class RecipeViewModel: ViewModel()