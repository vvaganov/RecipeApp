package com.example.recipeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.model.Recipe

@Dao
interface RecipeDao {

    @Insert
    fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe")
    fun getAllRecipe(): List<Recipe>
}