package com.example.recipeapp.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.ui.recipes.listRecipes.RecipeListAdapter

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()

    private val favoriteFragmentBinding:
            FragmentFavoritesBinding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return favoriteFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {

        viewModel.loadFavoritesList()

        val customAdapter = RecipeListAdapter(emptyList())
        favoriteFragmentBinding.rvRecipeFavoritesList.adapter = customAdapter

        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            if (state.favoritesSet?.isEmpty() == true) {
                favoriteFragmentBinding.tvEmptyFavoriteList.text =
                    getString(R.string.empty_favorite_list_message)
            } else {
                favoriteFragmentBinding.tvEmptyFavoriteList.visibility = View.GONE
                customAdapter.dataSet = state?.favoritesSet ?: emptyList()
            }
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
        val recipeId = FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipeId)
        findNavController().navigate(recipeId)
    }
}