package com.example.recipeapp.data

import android.content.SharedPreferences
import com.example.recipeapp.Constants.PREF_FAVORITE_KEY

class FavoritesLocalDataSources(
    private val sharedPreferences: SharedPreferences
) {

    fun getFavorites(): MutableSet<String> {
        val setId = sharedPreferences.getStringSet(PREF_FAVORITE_KEY, emptySet())
        val mutableSteId = HashSet<String>(setId)
        return mutableSteId
    }

    fun setFavorites(setFavoriteId: Set<String>?) {
        sharedPreferences.edit()?.putStringSet(PREF_FAVORITE_KEY, setFavoriteId)?.apply()
    }
}