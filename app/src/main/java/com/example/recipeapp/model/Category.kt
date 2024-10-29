package com.example.recipeapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "category")
data class Category(
    @PrimaryKey() val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
) : Parcelable