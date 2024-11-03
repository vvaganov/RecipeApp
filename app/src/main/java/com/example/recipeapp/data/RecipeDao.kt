package com.example.recipeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipeapp.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE cat_id = :categoryId")
    fun getAllRecipe(categoryId: Int): List<Recipe>

    @Update
    fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Recipe

    @Query("SELECT * FROM recipe WHERE isFavorites = :isFavorites")
    fun getFavoritesListRecipe(isFavorites: Boolean): List<Recipe>?
}