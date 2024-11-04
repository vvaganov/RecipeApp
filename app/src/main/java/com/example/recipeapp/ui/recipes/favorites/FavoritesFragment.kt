package com.example.recipeapp.ui.recipes.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.recipes.listRecipes.RecipeListAdapter

class FavoritesFragment : Fragment() {

    private val favoriteFragmentBinding:
            FragmentFavoritesBinding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (requireActivity().application as RecipeApplication).appContainer
        viewModel = appContainer.favoritesFactory.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return favoriteFragmentBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val customAdapter = RecipeListAdapter(emptyList())
        favoriteFragmentBinding.rvRecipeFavoritesList.adapter = customAdapter

        viewModel.loadFavoritesList()

        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->

            if (state.favoritesSet?.isEmpty() == true) {
                favoriteFragmentBinding.tvEmptyFavoriteList.text =
                    getString(R.string.empty_favorite_list_message)
            } else {
                if (state.favoritesSet != null) {
                    favoriteFragmentBinding.tvEmptyFavoriteList.visibility = View.GONE
                    customAdapter.dataSet = state.favoritesSet
                    customAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.load_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
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
        val recipe = FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipe)
        findNavController().navigate(recipe)
    }
}