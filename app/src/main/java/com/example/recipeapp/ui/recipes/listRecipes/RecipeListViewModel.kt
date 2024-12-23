package com.example.recipeapp.ui.recipes.listRecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipeListState = MutableLiveData(RecipeListUiState())
    val recipeListState: LiveData<RecipeListUiState> get() = _recipeListState

    fun loadRecipeList(category: Category) {

        viewModelScope.launch {
            fromCashRecipeList(category.id)
            val recipeListCloud = repository.getRecipeListByCategoryId(category.id)
            if (recipeListCloud?.size != repository.getRecipeListFromCash(category.id)?.size) {
                recipeListCloud?.forEach { recipe ->
                    repository.insertRecipeToCash(recipe.copy(categoryId = category.id))
                }
                fromCashRecipeList(category.id)
            }
        }
    }

    private suspend fun fromCashRecipeList(categoryId: Int) {
        _recipeListState.postValue(
            recipeListState.value?.copy(
                recipeList = repository.getRecipeListFromCash(categoryId)
            )
        )
    }
}

data class RecipeListUiState(
    val recipeList: List<Recipe>? = emptyList()
)