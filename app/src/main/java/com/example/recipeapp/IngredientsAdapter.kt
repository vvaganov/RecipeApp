package com.example.recipeapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemIngredientBinding
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.abs

class IngredientsAdapter(private val dataSet: List<Ingredient>?) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemIngredientBinding.bind(view)
        val textIngredientViewTitle: TextView = binding.tvIngredientTitle
        var textUnitOfMeasureViewTitle: TextView =
            binding.tvIngredientUnitOfMeasureTitle
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = dataSet?.get(position)
        val totalQuantity = BigDecimal(recipe?.quantity) * BigDecimal(quantity)

        val displayQuantity = totalQuantity
            .setScale(1, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()

        with(viewHolder) {
            textIngredientViewTitle.text = recipe?.description
            textUnitOfMeasureViewTitle.text =
                displayQuantity.toString() + " " + recipe?.unitOfMeasure
        }
    }

    fun updateIngredients(process: Int) {
        quantity = process
    }

    override fun getItemCount() = dataSet?.size ?: 0
}
