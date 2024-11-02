package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    private val recipeRepository = RecipeRepository(application)

    private val _favoritesState = MutableLiveData(FavoritesUiState(emptyList()))
    val favoritesState: LiveData<FavoritesUiState> get() = _favoritesState

    fun loadFavoritesList() {

        viewModelScope.launch {
            _favoritesState.postValue(
                favoritesState.value?.copy(
                    favoritesSet = recipeRepository.getFavoritesList(true) ?: emptyList()
                )
            )
        }
    }
}

data class FavoritesUiState(
    val favoritesSet: List<Recipe>?
)