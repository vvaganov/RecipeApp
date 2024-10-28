package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.launch

class CategoryListViewModel() : ViewModel() {

    private val repository = RecipeRepository()

    private val _categoryListState = MutableLiveData(CategoryListUiState())
    val categoryListState: LiveData<CategoryListUiState> get() = _categoryListState

    fun loadCategoryList() {

        viewModelScope.launch {
            _categoryListState.postValue(
                categoryListState.value?.copy(
                    categoryList = repository.getCategoryList()
                )
            )
        }
    }
}

data class CategoryListUiState(
    val categoryList: List<Category>? = emptyList()
)