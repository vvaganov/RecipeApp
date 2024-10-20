package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category

class CategoryListViewModel() : ViewModel() {

    private val repository = RecipeRepository()

    private val categoryList: MutableLiveData<List<Category>?> = MutableLiveData(emptyList())

    fun getCategoryList(): LiveData<List<Category>?> {
        loadCategoryList()
        return categoryList
    }

    private fun loadCategoryList() {
        repository.getCategoryList { list ->
            categoryList.postValue(list)
        }
    }
}

data class CategoryListUiState(
    val categoryList: List<Category>? = emptyList()
)