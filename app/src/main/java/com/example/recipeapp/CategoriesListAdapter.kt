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

class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick()
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener){
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewTitle: ImageView = view.findViewById(R.id.imgCategoryList)
        val textViewTitle: TextView = view.findViewById(R.id.tvTitle)
        val textViewDescription: TextView = view.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        try {
            val inputStream: InputStream? =
                viewHolder.itemView.context?.assets?.open(category.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.imageViewTitle.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("!!!", e.stackTrace.toString())
        }
        viewHolder.textViewTitle.text = category.title
        viewHolder.textViewDescription.text = category.description
        viewHolder.itemView.setOnClickListener{itemClickListener?.onItemClick()}
    }

    override fun getItemCount() = dataSet.size
}