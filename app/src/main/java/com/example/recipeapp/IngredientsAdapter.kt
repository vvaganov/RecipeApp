package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IngredientsAdapter(private val dataSet: List<Ingredient>?) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity: Int = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textIngredientViewTitle: TextView = view.findViewById(R.id.tvIngredientTitle)
        var textUnitOfMeasureViewTitle: TextView =
            view.findViewById(R.id.tvIngredientUnitOfMeasureTitle)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = dataSet?.get(position)
        val quantityDouble = recipe?.quantity?.toDouble()?.times(quantity)
        var quantity = ""
        if (quantityDouble != null) {
            quantity = if (quantityDouble % 1.0 == 0.0) {
                quantityDouble.toInt().toString()
            } else {
                quantityDouble.toString()
            }

            with(viewHolder) {
                textIngredientViewTitle.text = recipe?.description
                textUnitOfMeasureViewTitle.text = quantity + " " + recipe?.unitOfMeasure
            }
        }
    }

    fun updateIngredients(process: Int) {
        quantity = process
    }

    override fun getItemCount() = dataSet?.size ?: 0
}
