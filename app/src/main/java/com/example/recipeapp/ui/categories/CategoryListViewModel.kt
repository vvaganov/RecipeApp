package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.recipes.recipe.RecipeViewModel.RecipeUiState

class CategoryListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecipeRepository(context = application)

    private val _categoryListState = MutableLiveData(CategoryListUiState())
    val categoryListState: LiveData<CategoryListUiState> get() = _categoryListState

    fun loadCategoryList() {
        val categoryList = repository.getCategories()
        _categoryListState.value = categoryListState.value?.copy(categoryList = categoryList)
    }
}

data class CategoryListUiState(
    val categoryList: List<Category> = emptyList()
)