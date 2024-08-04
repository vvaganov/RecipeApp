package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import java.io.InputStream

class RecipeFragment : Fragment() {

    private val recipeBinding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return recipeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecipeArguments()
        initUI()
        initRecycler()
    }

    private fun initRecipeArguments() {
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("ARG_RECIPE", Recipe::class.java)
        } else {
            arguments?.getParcelable("ARG_RECIPE")
        }
    }

    private fun initRecycler() {
        val customAdapterIngredient = IngredientsAdapter(recipe?.ingredients)
        val customAdapterMethod = MethodAdapter(recipe?.method)
        with(recipeBinding) {
            rvIngredients.adapter = customAdapterIngredient
            rvMethod.adapter = customAdapterMethod
        }
        val recyclerViewIngredient = recipeBinding.rvIngredients
        val recyclerViewMethod = recipeBinding.rvMethod
        val divider = MaterialDividerItemDecoration(
            requireContext(),
            MaterialDividerItemDecoration.VERTICAL
        )
        val sizeInDp = resources.getDimensionPixelSize(R.dimen.indent_8)
        val color = resources.getColor(R.color.dividerLineColor, null)
        divider.dividerColor = color
        divider.dividerInsetStart = sizeInDp
        divider.dividerInsetEnd = sizeInDp
        divider.isLastItemDecorated = false
        recyclerViewIngredient.addItemDecoration(divider)
        recyclerViewMethod.addItemDecoration(divider)
    }

    private fun initUI() {
        with(recipeBinding) {
            tvRecipeTitle.text = recipe?.title
            try {
                val inputStream: InputStream? =
                    context?.assets?.open("${recipe?.imageUrl}")
                val drawable = Drawable.createFromStream(inputStream, null)
                imgRecipe.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.e("!!!", e.stackTrace.toString())
            }
        }
    }
}