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
        viewModelScope.launch {
            loadCategoryFromHash()
            val categoryListCloud = repository.getCategoryList()
            if (categoryListCloud
                    ?.equals(repository.getCategoryFromCash()) == false
            ) {
                categoryListCloud.forEach { category ->
                    repository.insertCashCategory(category)
                }
                loadCategoryFromHash()
            }
        }
    }

    private suspend fun loadCategoryFromHash() {
        _categoryListState.postValue(
            categoryListState.value?.copy(
                categoryList = repository.getCategoryFromCash()
            )
        )
    }
}

data class CategoryListUiState(
    val categoryList: List<Category>? = emptyList()
)