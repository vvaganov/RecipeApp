package com.example.recipeapp.data

import androidx.room.TypeConverter
import com.example.recipeapp.model.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientConverter {
    @TypeConverter
    fun mapListToString(value: List<Ingredient>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<Ingredient> {
        val gson = Gson()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(value, type)
    }
}

class MethodConverter {
    @TypeConverter
    fun fromMethod(method: List<String?>): String = method.joinToString(",")

    @TypeConverter
    fun toMethod(data: String): List<String> = data.split(",")
}