package com.example.recipeapp.ui.recipes.listRecipes

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import java.io.InputStream

class RecipeListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val repository = RecipeRepository()

    private val _recipeListState = MutableLiveData(RecipeListUiState())
    val recipeListState: LiveData<RecipeListUiState> get() = _recipeListState

    fun loadRecipeList(category: Category) {

        var drawable: Drawable? = null
        try {
            val inputStream: InputStream? =
                application.assets?.open(category.imageUrl)
            drawable = Drawable.createFromStream(inputStream, null)
        } catch (e: Exception) {
            Log.e("!!!", e.stackTrace.toString())
        }
        _recipeListState.value = recipeListState.value?.copy(
            titleImg = drawable,
            titleText = category.title,
            recipeList = getRecipesByCategoryId(category.id)
        )
    }

    private fun getRecipesByCategoryId(categoryId: Int?): List<Recipe> {
        val listRecipe = repository.getRecipe()
        return if (categoryId == 0) listRecipe else emptyList()
    }
}

data class RecipeListUiState(
    val titleImg: Drawable? = null,
    val titleText: String? = null,
    val recipeList: List<Recipe> = emptyList()
)