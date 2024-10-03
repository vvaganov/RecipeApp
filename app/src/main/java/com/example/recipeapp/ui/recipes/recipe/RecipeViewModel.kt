package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.FavoritesLocalDataSources
import com.example.recipeapp.data.FavoritesRepository
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import java.io.InputStream

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private val recipeRepository: RecipeRepository =
        RecipeRepository()

    private val favoritesRepository: FavoritesRepository =
        FavoritesRepository(favoritesLocalDataSources = FavoritesLocalDataSources((application)))

    private val _recipeState = MutableLiveData(RecipeUiState())
    val recipeState: LiveData<RecipeUiState> get() = _recipeState

    fun loadRecipe(recipeId: Int?) {
        //TODO `load from network`
        val isFavorite = checkIsFavorites(recipeId)
        val recipe = recipeRepository.getRecipeById(recipeId)
        var drawable: Drawable? = null

        try {
            val inputStream: InputStream? =
                application.assets?.open("${recipe?.imageUrl}")
            drawable = Drawable.createFromStream(inputStream, null)
        } catch (e: Exception) {
            Log.e("!!!", e.stackTrace.toString())
        }
        _recipeState.value = recipeState.value?.copy(
            recipe = recipe,
            isFavorites = isFavorite,
            recipeImage = drawable
        )
    }

    fun onFavoritesClicked(recipeId: Int?) {
        val favoriteSet = favoritesRepository.getRecipeData()
        val newSet = if (checkIsFavorites(recipeId)) {
            favoriteSet.minus(recipeId.toString())
        } else {
            favoriteSet.plus(recipeId.toString())
        }
        favoritesRepository.setRecipeData(newSet)
        _recipeState.value = recipeState.value?.copy(isFavorites = checkIsFavorites(recipeId))
    }

    private fun checkIsFavorites(recipeId: Int?): Boolean {
        return favoritesRepository.getRecipeData().toList().contains(recipeId.toString())
    }

    fun changeNumberOfServing(progress: Int) {
        _recipeState.value = recipeState.value?.copy(numberServings = progress)
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val numberServings: Int = 1,
        val recipeImage: Drawable? = null
    )
}