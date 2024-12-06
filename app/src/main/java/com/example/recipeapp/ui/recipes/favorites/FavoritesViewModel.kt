package com.example.recipeapp.ui.recipes.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {

    private val _favoritesState = MutableLiveData(FavoritesUiState(emptyList()))
    val favoritesState: LiveData<FavoritesUiState> get() = _favoritesState

    fun loadFavoritesList() {

        viewModelScope.launch {
            _favoritesState.postValue(
                favoritesState.value?.copy(
                    favoritesSet = repository.getFavoritesList(true) ?: emptyList()
                )
            )
        }
    }
}

data class FavoritesUiState(
    val favoritesSet: List<Recipe>?
)