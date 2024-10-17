package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.Constants.PREF_FILE_NAME
import com.example.recipeapp.data.FavoritesLocalDataSources
import com.example.recipeapp.data.FavoritesRepository
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    private val recipeRepository = RecipeRepository()
    private val favoritesRepository = FavoritesRepository(
        FavoritesLocalDataSources(
            (application.getSharedPreferences(
                PREF_FILE_NAME, MODE_PRIVATE
            ))
        )
    )

    private val _favoritesState = MutableLiveData(FavoritesUiState())
    val favoritesState: LiveData<FavoritesUiState> get() = _favoritesState

    fun loadFavoritesList() {
        val favoritesRecipeSetId = favoritesRepository.getRecipeData().map { it.toInt() }.toSet()
        Log.i("!!!", "SetSize = ${favoritesRecipeSetId.size}")

        var favoriteRecipeList: List<Recipe>? = emptyList()

        recipeRepository.getListRecipeByListId(favoritesRecipeSetId) { recipeList ->
            favoriteRecipeList = recipeList
        }

        if (favoriteRecipeList?.isNotEmpty() == true) {
            _favoritesState.value = favoritesState.value?.copy(
                favoritesSet = favoriteRecipeList
            )
        }
    }
}

data class FavoritesUiState(
    val favoritesSet: List<Recipe>? = emptyList(),
)