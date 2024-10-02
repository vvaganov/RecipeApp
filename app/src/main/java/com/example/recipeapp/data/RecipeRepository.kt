package com.example.recipeapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.recipeapp.PREF_FAVORITE_KEY
import com.example.recipeapp.PREF_FILE_NAME
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

class RecipeRepository(
    private val recipe: STUB = STUB,
    private val context: Context
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

    fun getFavorites(): MutableSet<String> {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val setId = sharedPref.getStringSet(PREF_FAVORITE_KEY, emptySet())
        val mutableSteId = HashSet<String>(setId)
        return mutableSteId
    }

    fun setFavorites(setFavoriteId: Set<String>?) {
        val sharedPref:SharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
            ?: return
        sharedPref.edit()?.putStringSet(PREF_FAVORITE_KEY, setFavoriteId)?.apply()
    }
}