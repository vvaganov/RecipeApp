package com.example.recipeapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.Constants.BASE_API_URL
import com.example.recipeapp.data.AppDatabase
import com.example.recipeapp.data.CategoryDao
import com.example.recipeapp.data.RecipeApiService
import com.example.recipeapp.data.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

    @IoDispatcher
    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-recipe_app"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): RecipeDao = appDatabase.recipeDao()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): RecipeApiService =
        retrofit.create(RecipeApiService::class.java)
}