package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("recipe/{id}")
    fun getRecipeById(@Path("id") recipeId: Int): Call<Recipe>

    @GET("category/{id}")
    fun getCategoryById(@Path("id") categoryId: Int): Call<Category>

    @GET("category/{id}/recipes")
    fun getListRecipeByCategoryId(@Path("id") categoryId: Int): Call<List<Recipe>>

    @GET("recipes")
    fun getListRecipeByListId(@Query("ids") listId: String): Call<List<Recipe>>
}