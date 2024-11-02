package com.example.recipeapp.data

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.Constants.BASE_API_URL
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository(context: Context) {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-recipe_app"
    ).fallbackToDestructiveMigration()
        .build()
    private val recipeDao by lazy { db.recipeDao() }
    private val categoryDao by lazy { db.categoryDao() }


    suspend fun insertCashCategory(category: Category) = withContext(Dispatchers.IO) {
        categoryDao.insertCategory(category)
    }

    suspend fun getCategoryFromCash(): List<Category>? = withContext(Dispatchers.IO) {
        try {
            categoryDao.getAllCategory()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCategoryList(): List<Category>? = withContext(Dispatchers.IO) {
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

    suspend fun insertRecipeToCash(recipe: Recipe) = withContext(Dispatchers.IO) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun getRecipeListByCategoryId(categoryId: Int): List<Recipe>? =
        withContext(Dispatchers.IO) {
            try {
                service.getListRecipeByCategoryId(categoryId).execute().body()
            } catch (e: Exception) {
                null
            }
        }

    suspend fun getRecipeById(recipeId: Int): Recipe? = withContext(Dispatchers.IO) {
        try {
            service.getRecipeById(recipeId).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCategoryById(categoryId: Int) = withContext(Dispatchers.IO) {
        service.getCategoryById(categoryId).execute().body()
    }

    suspend fun getListRecipeByListId(favoritesListIdInt: String): List<Recipe>? =
        withContext(Dispatchers.IO) {
            try {
                service.getListRecipeByListId(favoritesListIdInt).execute().body() ?: emptyList()
            } catch (e: Exception) {
                null
            }
        }
}
