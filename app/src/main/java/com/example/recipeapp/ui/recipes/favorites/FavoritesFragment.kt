package com.example.recipeapp.ui.recipes.favorites

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.ARG_RECIPE
import com.example.recipeapp.ARG_RECIPE_ID
import com.example.recipeapp.PREF_FAVORITE_KEY
import com.example.recipeapp.PREF_FILE_NAME
import com.example.recipeapp.R
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
import com.example.recipeapp.ui.recipes.listRecipes.RecipeListAdapter
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

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
        initRecycler()
    }

    private fun initRecycler() {
        val favoritesRecipeSetId = getFavorites().map { it.toInt() }.toSet()
        val favoriteRecipeList = STUB.getRecipesByIds(favoritesRecipeSetId)
        if (favoriteRecipeList.none()) {
            favoriteFragmentBinding.tvEmptyFavoriteList.text =
                getString(R.string.empty_favorite_list_message)

        } else {
            favoriteFragmentBinding.tvEmptyFavoriteList.visibility = View.GONE
            val customAdapter = RecipeListAdapter(favoriteRecipeList)
            favoriteFragmentBinding.rvRecipeFavoritesList.adapter = customAdapter
            customAdapter.setOnItemClickListener(
                object : RecipeListAdapter.OnItemClickListener {
                    override fun onItemClick(recipeId: Int) {
                        openRecipeByRecipeId(recipeId)
                    }
                }
            )
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPref =
            activity?.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val setId = sharedPref?.getStringSet(PREF_FAVORITE_KEY, emptySet())
        val mutableSteId = HashSet<String>(setId)
        return mutableSteId
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val bundle = bundleOf(
            ARG_RECIPE_ID to recipeId,
        )
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }
}