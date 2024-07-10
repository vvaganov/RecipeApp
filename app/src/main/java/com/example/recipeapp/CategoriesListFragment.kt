package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.databinding.FragmentListCategoriesBinding


class CategoriesListFragment : Fragment() {

    private var _categoriesListBinding: FragmentListCategoriesBinding? = null
    private val categoriesListBinding get() = _categoriesListBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _categoriesListBinding = FragmentListCategoriesBinding.inflate(inflater,container,false)
        val view  = categoriesListBinding.root
        return view
    }
}
