package com.example.recipeapp.ui.recipes.listRecipes

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val repository = RecipeRepository(application)

    private val _recipeListState = MutableLiveData(RecipeListUiState())
    val recipeListState: LiveData<RecipeListUiState> get() = _recipeListState

    fun loadRecipeList(category: Category) {

        viewModelScope.launch {
            fromHashRecipeList(category.id)
            val recipeListCloud = repository.getRecipeListByCategoryId(category.id)
            if (recipeListCloud?.equals(repository.getRecipeListFromHash(category.id)) == false) {
                recipeListCloud.forEach { recipe ->
                    repository.insertRecipeToHash(recipe.copy(categoryId = category.id))
                }
                fromHashRecipeList(category.id)
            }
        }
    }

    private suspend fun fromHashRecipeList(categoryId: Int) {
        Log.i("!!!", "id category - $categoryId")

        _recipeListState.postValue(
            recipeListState.value?.copy(
                recipeList = repository.getRecipeListFromHash(categoryId)
            )
        )
    }
}

data class RecipeListUiState(
    val recipeList: List<Recipe>? = emptyList()
)