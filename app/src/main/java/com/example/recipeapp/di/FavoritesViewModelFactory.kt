package com.example.recipeapp.di

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.ui.recipes.favorites.FavoritesViewModel

class FavoritesViewModelFactory(
    private val recipeRepository: RecipeRepository
) : Factory<FavoritesViewModel> {
    override fun onCreate(): FavoritesViewModel {
        return FavoritesViewModel(recipeRepository)
    }
}