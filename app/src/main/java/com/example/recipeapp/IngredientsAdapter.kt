package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IngredientsAdapter(private val dataSet: List<Ingredient>?) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textIngredientViewTitle: TextView = view.findViewById(R.id.tvIngredientTitle)
        val textUnitOfMeasureViewTitle: TextView =
            view.findViewById(R.id.tvIngredientUnitOfMeasureTitle)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = dataSet?.get(position)

        with(viewHolder) {
            textIngredientViewTitle.text = recipe?.description
            textUnitOfMeasureViewTitle.text = recipe?.quantity + " " + recipe?.unitOfMeasure
        }
    }

    override fun getItemCount() = dataSet?.size ?: 0
}