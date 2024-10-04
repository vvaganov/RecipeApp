package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

class RecipeRepository(
    private val recipe: STUB = STUB,
) {

    private val category = recipe.getCategories()
    private val burgerRecipes = recipe.getRecipe()

    fun getCategories() = category

    fun getRecipe() = burgerRecipes

    fun getRecipeById(id: Int?): Recipe? {
        return burgerRecipes.find { it.id == id }
    }

    fun getCategoryById(id: Int?): Category? {
        return category.find { it.id == id }
    }

    fun getRecipesByIds(favoritesListIdInt: Set<Int>): List<Recipe> {
        return burgerRecipes.filter { favoritesListIdInt.contains(it.id) }
    }
}