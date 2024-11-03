package com.example.recipeapp.di

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.ui.categories.CategoryListViewModel

class CategoryListViewModelFactory(
    private val recipeRepository: RecipeRepository
) : Factory<CategoryListViewModel> {

    override fun onCreate(): CategoryListViewModel {
        return CategoryListViewModel(recipeRepository)
    }
}