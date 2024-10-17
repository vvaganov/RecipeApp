package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.model.Category

class CategoriesListFragment : Fragment() {

    private val viewModel: CategoryListViewModel by viewModels()

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
        initUi()
    }

    private fun initUi() {

        viewModel.loadCategoryList()

        val customAdapter = CategoriesListAdapter(emptyList())
        categoriesListBinding.rvCategories.adapter = customAdapter

        viewModel.categoryListState.observe(viewLifecycleOwner) { state ->
            customAdapter.dataSet = state.categoryList ?: emptyList()
        }
        customAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(category: Category) {
                    openRecipesListByCategoryId(category)
                }
            }
        )
    }

    private fun openRecipesListByCategoryId(category: Category) {

        if (category != null) {
            val category =
                CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                    category
                )
            findNavController().navigate(category)
        } else {
            throw IllegalArgumentException("There is no such category")
        }
    }
}