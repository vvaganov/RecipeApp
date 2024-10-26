package com.example.recipeapp.data

import com.example.recipeapp.Constants.BASE_API_URL
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getCategoryList(callback: (List<Category>?) -> Unit) {

        withContext(Dispatchers.IO) {
            try {
                val categoryList = service.getCategories().execute().body()
                callback(categoryList ?: emptyList())
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    suspend fun getRecipeListByCategoryId(
        categoryId: Int,
        callback: (List<Recipe>?) -> Unit
    ) {

        withContext(Dispatchers.IO) {
            try {
                val recipeList = service.getListRecipeByCategoryId(categoryId).execute().body()
                callback(recipeList)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    suspend fun getRecipeById(recipeId: Int, callback: (Recipe?) -> Unit) {

        withContext(Dispatchers.IO) {
            try {
                val recipe = service.getRecipeById(recipeId).execute().body()
                callback(recipe)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }


    suspend fun getCategoryById(categoryId: Int) {
        withContext(Dispatchers.IO) {

            val category = service.getCategoryById(categoryId).execute().body()
        }
    }

    suspend fun getListRecipeByListId(
        favoritesListIdInt: String,
        callback: (List<Recipe>?) -> Unit
    ) {

        withContext(Dispatchers.IO) {

            try {
                val listRecipe = service.getListRecipeByListId(favoritesListIdInt).execute().body()
                callback(listRecipe)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }
}