package com.example.recipeapp.data

import com.example.recipeapp.Constants.BASE_API_URL
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository {

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getCategoryList(callback: (List<Category>?) -> Unit) {

        threadPool.submit {
            try {
                val categoryList = service.getCategories().execute().body()
                callback(categoryList)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    fun getRecipeListByCategoryId(
        categoryId: Int,
        callback: (List<Recipe>?) -> Unit
    ) {
        threadPool.submit {
            try {
                val recipeList = service.getListRecipeByCategoryId(categoryId).execute().body()
                callback(recipeList)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    fun getRecipeById(recipeId: Int, callback: (Recipe?) -> Unit) {
        threadPool.submit {
            try {
                val recipe = service.getRecipeById(recipeId).execute().body()
                callback(recipe)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    fun getCategoryById(categoryId: Int) {
        threadPool.submit {
            val category = service.getCategoryById(categoryId).execute().body()
        }
    }

    fun getListRecipeByListId(favoritesListIdInt: String, callback: (List<Recipe>?) -> Unit) {
        threadPool.submit {
            try {
                val listRecipe = service.getListRecipeByListId(favoritesListIdInt).execute().body()
                callback(listRecipe)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }
}