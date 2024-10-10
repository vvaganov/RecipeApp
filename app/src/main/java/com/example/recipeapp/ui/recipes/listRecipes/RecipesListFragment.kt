package com.example.recipeapp.ui.recipes.listRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.Constants.ARG_CATEGORY_ID
import com.example.recipeapp.databinding.FragmentRecipesListBinding

class RecipesListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    private val recipesListBinding:
            FragmentRecipesListBinding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }

    private val categoryId: Int
        get() = arguments?.getInt(ARG_CATEGORY_ID)
            ?: throw IllegalArgumentException("argument is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return recipesListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        viewModel.loadRecipeList(categoryId)

        val customAdapter = RecipeListAdapter(emptyList())
        recipesListBinding.rvRecipeList.adapter = customAdapter

        viewModel.recipeListState.observe(viewLifecycleOwner) { state ->
            recipesListBinding.tvRecipeList.text = state.titleText
            recipesListBinding.imgRecipeList.setImageDrawable(state.titleImg)
            customAdapter.dataSet = state.recipeList
        }
        customAdapter.setOnItemClickListener(
            object : RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(recipeId)
                }
            }
        )
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipeId =
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipeId)
        findNavController().navigate(recipeId)
    }
}