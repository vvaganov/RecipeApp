package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val service: RecipeApiService,
    private val ioDispatcher: CoroutineContext
) {
    suspend fun insertCashCategory(category: Category) = withContext(ioDispatcher) {
        categoryDao.insertCategory(category)
    }

    suspend fun getCategoryFromCash(): List<Category>? = withContext(ioDispatcher) {
        try {
            categoryDao.getAllCategory()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCategoryList(): List<Category>? = withContext(ioDispatcher) {
        try {
            service.getCategories().execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getRecipeListFromCash(categoryId: Int): List<Recipe>? =
        withContext(Dispatchers.IO) {
            try {
                recipeDao.getAllRecipe(categoryId)
            } catch (e: Exception) {
                null
            }
        }

    suspend fun insertRecipeToCash(recipe: Recipe) = withContext(ioDispatcher) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun getRecipeListByCategoryId(categoryId: Int): List<Recipe>? =
        withContext(ioDispatcher) {
            try {
                service.getListRecipeByCategoryId(categoryId).execute().body()
            } catch (e: Exception) {
                null
            }
        }

    suspend fun getRecipeByIdFromCash(recipeId: Int): Recipe = withContext(ioDispatcher) {
        recipeDao.getRecipeById(recipeId)
    }

    suspend fun getRecipeById(recipeId: Int): Recipe? = withContext(ioDispatcher) {
        try {
            service.getRecipeById(recipeId).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCategoryById(categoryId: Int) = withContext(ioDispatcher) {
        service.getCategoryById(categoryId).execute().body()
    }

    suspend fun getListRecipeByListId(favoritesListIdInt: String): List<Recipe>? =
        withContext(ioDispatcher) {
            try {
                service.getListRecipeByListId(favoritesListIdInt).execute().body() ?: emptyList()
            } catch (e: Exception) {
                null
            }
        }

    suspend fun updateRecipe(recipe: Recipe) = withContext(ioDispatcher) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun getFavoritesList(isFavorites: Boolean): List<Recipe>? =
        withContext(ioDispatcher) {
            recipeDao.getFavoritesListRecipe(isFavorites)

        }

}
