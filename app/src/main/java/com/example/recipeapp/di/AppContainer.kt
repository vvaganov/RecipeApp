package com.example.recipeapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.Constants.BASE_API_URL
import com.example.recipeapp.data.AppDatabase
import com.example.recipeapp.data.RecipeApiService
import com.example.recipeapp.data.RecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-recipe_app"
    ).fallbackToDestructiveMigration()
        .build()

    private val recipeDao by lazy { db.recipeDao() }
    private val categoryDao by lazy { db.categoryDao() }
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val repository = RecipeRepository(
        recipeDao = recipeDao,
        categoryDao = categoryDao,
        service = service,
        ioDispatcher = ioDispatcher
    )

    val categoryListFactory = CategoryListViewModelFactory(repository)
    val recipeListFactory = RecipeListViewModelFactory(repository)
    val recipeFactory = RecipeViewModelFactory(repository)
    val favoritesFactory = FavoritesViewModelFactory(repository)
}