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

    private var quantity = BigDecimal("1")

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
        val quantityResult = BigDecimal(recipe?.quantity).times(quantity)
        val remainderDivision = quantityResult.rem(BigDecimal(1))

        with(viewHolder) {
            textIngredientViewTitle.text = recipe?.description
            textUnitOfMeasureViewTitle.text =
                quantityResult.toString() + " " + recipe?.unitOfMeasure
        }
    }

    fun updateIngredients(process: Int) {
        quantity = process.toBigDecimal()
    }

    override fun getItemCount() = dataSet?.size ?: 0
}
