package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.PREF_FAVORITE_KEY
import com.example.recipeapp.PREF_FILE_NAME
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

private const val MESSAGE_EMPTY_FAVORITE_LIST = "Вы еще не добавили ни одного рецепта в избранное"

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = RecipeRepository(context = application)

    private val _favoritesState = MutableLiveData(FavoritesUiState())
    val favoritesState: LiveData<FavoritesUiState> get() = _favoritesState

    fun loadFavoritesList() {
        val favoritesRecipeSetId = repository.getFavorites().map { it.toInt() }.toSet()
        val favoriteRecipeList = repository.getRecipesByIds(favoritesRecipeSetId)
        if (favoriteRecipeList.isNotEmpty()) {
            _favoritesState.value = favoritesState.value?.copy(
                visibilityText = View.GONE,
                favoritesSet = favoriteRecipeList
            )
        }
    }
}

data class FavoritesUiState(
    val favoritesSet: List<Recipe> = emptyList(),
    val visibilityText: Int = View.VISIBLE,
    val messageText: String = MESSAGE_EMPTY_FAVORITE_LIST
)