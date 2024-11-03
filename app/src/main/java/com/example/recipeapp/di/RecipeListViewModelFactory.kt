package com.example.recipeapp.di

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.ui.recipes.listRecipes.RecipeListViewModel

class RecipeListViewModelFactory(
    private val recipeRepository: RecipeRepository
) : Factory<RecipeListViewModel> {

    override fun onCreate(): RecipeListViewModel {
        return RecipeListViewModel(recipeRepository)
    }
}