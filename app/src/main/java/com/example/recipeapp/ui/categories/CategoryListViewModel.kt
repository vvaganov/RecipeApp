package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.launch

class CategoryListViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = RecipeRepository(application)

    private val _categoryListState = MutableLiveData(CategoryListUiState())
    val categoryListState: LiveData<CategoryListUiState> get() = _categoryListState

    fun loadCategoryList() {
        changeState()
        viewModelScope.launch {
            repository.getCategoryList()?.forEach { category ->
                repository.insertHashCategory(category)
                changeState()
            }
        }
    }

    private fun changeState() {
        viewModelScope.launch {
            _categoryListState.postValue(
                categoryListState.value?.copy(
                    categoryList = repository.getCategoryFromHash()
                )
            )
        }
    }
}

data class CategoryListUiState(
    val categoryList: List<Category>? = emptyList()
)