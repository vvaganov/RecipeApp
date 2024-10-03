package com.example.recipeapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class FavoritesLocalDataSources(
    private val context: Context,
) {

    fun getFavorites(): MutableSet<String> {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val setId = sharedPref.getStringSet(PREF_FAVORITE_KEY, emptySet())
        val mutableSteId = HashSet<String>(setId)
        return mutableSteId
    }

    fun setFavorites(setFavoriteId: Set<String>?) {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
                ?: return
        sharedPref.edit()?.putStringSet(PREF_FAVORITE_KEY, setFavoriteId)?.apply()
    }

    companion object {
        const val PREF_FAVORITE_KEY = "favoritesId"
        const val PREF_FILE_NAME = "preferencesFavoritesId"
    }
}