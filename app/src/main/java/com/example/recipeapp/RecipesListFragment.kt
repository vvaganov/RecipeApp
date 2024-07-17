package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RecipesListFragment : Fragment() {

    private var itemId: Int? = null
    private var title: String? = null
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemId = this.requireArguments().getInt("ARG_CATEGORY_ID")
        title = this.requireArguments().getString("ARG_CATEGORY_NAME")
        imageUrl = this.requireArguments().getString("ARG_CATEGORY_IMAGE_URL")
        return inflater.inflate(R.layout.fragment_recipes_list, container, false)
    }
}