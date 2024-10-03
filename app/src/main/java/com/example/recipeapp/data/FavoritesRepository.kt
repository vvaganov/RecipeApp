package com.example.recipeapp.data

class FavoritesRepository(
    private val favoritesLocalDataSources: FavoritesLocalDataSources,
) {

    fun getRecipeData():MutableSet<String>{
        return favoritesLocalDataSources.getFavorites()
    }

    fun setRecipeData(setFavoriteId: Set<String>?){
        favoritesLocalDataSources.setFavorites(setFavoriteId)
    }
}