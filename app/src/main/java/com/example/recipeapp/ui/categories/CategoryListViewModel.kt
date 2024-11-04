package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {

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