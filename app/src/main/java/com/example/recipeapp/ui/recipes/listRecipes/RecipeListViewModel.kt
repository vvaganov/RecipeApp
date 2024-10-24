package com.example.recipeapp.ui.recipes.listRecipes

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

class RecipeListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val repository = RecipeRepository()

    private val _recipeListState = MutableLiveData(RecipeListUiState())
    val recipeListState: LiveData<RecipeListUiState> get() = _recipeListState

    fun loadRecipeList(category: Category): LiveData<RecipeListUiState> {

        repository.getRecipeListByCategoryId(category.id) { recipeList ->
            _recipeListState.postValue(
                recipeListState.value?.copy(
                    recipeList = recipeList
                )
            )
        }
        return recipeListState
    }
}

data class RecipeListUiState(
    val recipeList: List<Recipe>? = emptyList()
)