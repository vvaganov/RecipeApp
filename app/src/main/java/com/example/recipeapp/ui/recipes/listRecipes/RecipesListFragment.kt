package com.example.recipeapp.ui.recipes.listRecipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.recipeapp.Constants.ARG_CATEGORY_ID
import com.example.recipeapp.Constants.ARG_RECIPE_ID
import com.example.recipeapp.R
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
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
        val bundle = bundleOf(ARG_RECIPE_ID to recipeId)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }
}