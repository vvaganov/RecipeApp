package com.example.recipeapp.ui.categories

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemCategoryBinding
import com.example.recipeapp.model.Category
import java.io.InputStream

class CategoriesListAdapter(var dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCategoryBinding.bind(view)
        val imageViewTitle: ImageView = binding.imgCategoryList
        val textViewTitle: TextView = binding.tvTitle
        val textViewDescription: TextView = binding.tvDescription
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        with(viewHolder) {
            try {
                val inputStream: InputStream? =
                    itemView.context?.assets?.open(category.imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                viewHolder.imageViewTitle.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.e("!!!", e.stackTrace.toString())
            }
            textViewTitle.text = category.title
            textViewDescription.text = category.description
            itemView.setOnClickListener { itemClickListener?.onItemClick(category.id) }
        }
    }

    override fun getItemCount() = dataSet.size
}