package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository {

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val contentType = "application/json".toMediaType()
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://recipes.androidsprint.ru/api/")
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    fun getCategoryList(callback: (List<Category>?) -> Unit) {

        threadPool.submit {
            try {
                val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
                val categoryList = service.getCategories().execute().body()
                callback(categoryList)
            } catch (e: Exception) {
                callback(null)
            }
        }.get()
    }

    fun getRecipeListByCategoryId(
        categoryId: Int,
        callback: (List<Recipe>?) -> Unit
    ) {
        threadPool.submit {
            try {
                val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
                val recipeList = service.getListRecipeByCategoryId(categoryId).execute().body()
                callback(recipeList)
            } catch (e: Exception) {
                callback(null)
            }
        }.get()

    }

    fun getRecipeById(recipeId: Int, callback: (Recipe?) -> Unit) {
        threadPool.submit {
            try {
                val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
                val recipe = service.getRecipeById(recipeId).execute().body()
                callback(recipe)
            } catch (e: Exception) {
                callback(null)
            }
        }.get()
    }

    fun getCategoryById(categoryId: Int) {
        threadPool.submit {
            val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
            val category = service.getCategoryById(categoryId).execute().body()
        }
    }

    fun getListRecipeByListId(favoritesListIdInt: Set<Int>, callback: (List<Recipe>?) -> Unit) {
        threadPool.submit {
            val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
            val listRecipe = service.getListRecipeByListId(favoritesListIdInt).execute().body()
            callback(listRecipe)
        }.get()
    }
}