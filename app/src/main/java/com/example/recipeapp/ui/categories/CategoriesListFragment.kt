package com.example.recipeapp.ui.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customAdapter = CategoriesListAdapter(emptyList())
        categoriesListBinding.rvCategories.adapter = customAdapter

        viewModel.loadCategoryList()

        viewModel.categoryListState.observe(viewLifecycleOwner) { state ->
            if (state.categoryList != null) {
                customAdapter.dataSet = state.categoryList
                Log.i("!!!", "Обсервер ${state.categoryList}")
                customAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), getString(R.string.load_error), Toast.LENGTH_SHORT)
                    .show()
            }

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
        val navDirection =
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                category
            )
        findNavController().navigate(navDirection)
    }
}