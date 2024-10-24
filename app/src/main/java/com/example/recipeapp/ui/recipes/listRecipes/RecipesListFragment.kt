package com.example.recipeapp.ui.recipes.listRecipes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import com.example.recipeapp.model.Recipe

class RecipesListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    private val recipesListBinding:
            FragmentRecipesListBinding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }
    private val args: RecipesListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return recipesListBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.category

        val customAdapter = RecipeListAdapter(emptyList())
        recipesListBinding.rvRecipeList.adapter = customAdapter

        viewModel.loadRecipeList(category)

        recipesListBinding.tvRecipeList.text = category.title
        recipesListBinding.imgRecipeList.setImageDrawable(viewModel.getImage(category))

        viewModel.recipeListState.observe(viewLifecycleOwner) { state ->
            if (state.recipeList != null) {
                customAdapter.dataSet = state.recipeList
                customAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), getString(R.string.load_error), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        customAdapter.setOnItemClickListener(
            object : RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipe: Recipe) {
                    openRecipeByRecipeId(recipe)
                }
            }
        )
    }

    private fun openRecipeByRecipeId(recipe: Recipe) {
        val recipe =
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipe)
        findNavController().navigate(recipe)
    }
}