package com.example.recipeapp.ui.recipes.listRecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.Constants.BASE_API_IMAGE_URL
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemRecipeBinding
import com.example.recipeapp.model.Recipe

class RecipeListAdapter(var dataSet: List<Recipe>) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecipeBinding.bind(view)
        val imageViewTitle: ImageView = binding.imgRecipeList
        val textViewTitle: TextView = binding.tvRecipeListTitle
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        with(viewHolder) {
            Glide.with(itemView.context)
                .load(BASE_API_IMAGE_URL + recipe.imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(imageViewTitle)
            textViewTitle.text = recipe.title
            itemView.setOnClickListener { itemClickListener?.onItemClick(recipe) }
        }
    }

    override fun getItemCount() = dataSet.size
}