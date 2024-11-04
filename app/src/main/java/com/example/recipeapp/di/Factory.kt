package com.example.recipeapp.di

interface Factory<T> {
    fun onCreate(): T
}