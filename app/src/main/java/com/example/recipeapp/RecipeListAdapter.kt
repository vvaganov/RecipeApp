package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream

class RecipeListAdapter(private val dataSet: List<Recipe>) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewTitle: ImageView = view.findViewById(R.id.imgRecipeList)
        val textViewTitle: TextView = view.findViewById(R.id.tvRecipeListTitle)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        with(viewHolder) {
            try {
                val inputStream: InputStream? =
                    itemView.context?.assets?.open(recipe.imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                viewHolder.imageViewTitle.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.e("!!!", e.stackTrace.toString())
            }
            textViewTitle.text = recipe.title
            itemView.setOnClickListener { itemClickListener?.onItemClick(recipe.id) }
        }
    }

    override fun getItemCount() = dataSet.size
}