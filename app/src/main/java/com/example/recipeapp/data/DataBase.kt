package com.example.recipeapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

@Database(entities = [Category::class, Recipe::class], version = 2)
@TypeConverters(value = [IngredientConverter::class, MethodConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}