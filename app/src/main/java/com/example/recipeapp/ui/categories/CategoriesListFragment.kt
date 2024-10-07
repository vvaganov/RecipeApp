package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.Constants.ARG_CATEGORY_ID
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

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
            customAdapter.dataSet = state.categoryList
        }
        customAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(categoryId)
                }
            }
        )
    }

    private fun openRecipesByCategoryId(categoryId: Int) {

        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
        )
        findNavController().navigate(
            R.id.action_categoriesListFragment_to_recipesListFragment,
            bundle
        )
    }
}