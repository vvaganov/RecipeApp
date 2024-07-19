package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RecipesListFragment : Fragment() {

    private var categoryId: Int? = null
    private var title: String? = null
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBundleData()
        return inflater.inflate(R.layout.fragment_recipes_list, container, false)
    }

    private fun initBundleData(){
        arguments.let{
            categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
            title = requireArguments().getString(ARG_CATEGORY_NAME)
            imageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)
        }
    }
}