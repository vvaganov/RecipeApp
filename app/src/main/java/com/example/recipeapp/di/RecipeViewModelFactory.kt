package com.example.recipeapp.di

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.ui.recipes.recipe.RecipeViewModel

class RecipeViewModelFactory(
    private val recipeRepository: RecipeRepository
) : Factory<RecipeViewModel> {
    override fun onCreate(): RecipeViewModel {
        return RecipeViewModel(recipeRepository)
    }
}