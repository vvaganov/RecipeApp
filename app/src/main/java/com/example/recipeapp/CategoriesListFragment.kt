package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment() {

    private val categoriesListBinding:
            FragmentListCategoriesBinding by lazy {
        FragmentListCategoriesBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return categoriesListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val customAdapter = CategoriesListAdapter(STUB.getCategories())
        categoriesListBinding.rvCategories.adapter = customAdapter
    }
}