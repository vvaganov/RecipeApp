package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MethodAdapter(private val dataSet: List<String>?) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textMethodViewTitle: TextView = view.findViewById(R.id.tvMethodText)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_method, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val description = dataSet?.get(position)
        viewHolder.textMethodViewTitle.text = "${position + 1}. $description"

    }

    override fun getItemCount() = dataSet?.size ?: 0
}