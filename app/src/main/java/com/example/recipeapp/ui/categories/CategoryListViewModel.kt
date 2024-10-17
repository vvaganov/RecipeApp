package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category

class CategoryListViewModel() : ViewModel() {

    private val repository = RecipeRepository()

    private val _categoryListState = MutableLiveData(CategoryListUiState())
    val categoryListState: LiveData<CategoryListUiState> get() = _categoryListState

    fun loadCategoryList() {

        var list: List<Category>? = emptyList()

        repository.getCategoryList() { categoryList ->
            list = categoryList
        }
        _categoryListState.value = categoryListState.value?.copy(
            categoryList = list
        )
    }
}

data class CategoryListUiState(
    val categoryList: List<Category>? = emptyList()
)