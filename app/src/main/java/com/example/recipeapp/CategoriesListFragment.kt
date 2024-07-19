package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
        customAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(categoryId)
                }
            }
        )
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category = STUB.getCategories()
        val categoryName: String = category.first { it.id == categoryId }.title
        val categoryImageUrl: String = category.first { it.id == categoryId }.imageUrl

        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl,
        )
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
        }
    }
}