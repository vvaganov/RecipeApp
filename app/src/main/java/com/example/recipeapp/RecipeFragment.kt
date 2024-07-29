package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.databinding.FragmentRecipesListBinding


class RecipeFragment : Fragment() {

    private val recipeBinding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return recipeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("ARG_RECIPE", Recipe::class.java)
        } else {
            arguments?.getParcelable("ARG_RECIPE")
        }
        recipeBinding.tvRecipeTitle.text = recipe?.title
    }
}