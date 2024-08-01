package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    ): View? {
        return recipeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecipeArguments()
        initUi()
        initRecycler(view)
    }

    private fun initRecipeArguments() {
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("ARG_RECIPE", Recipe::class.java)
        } else {
            arguments?.getParcelable("ARG_RECIPE")
        }
    }

    private fun initRecycler(view: View) {
        val customAdapterIngredient = IngredientsAdapter(recipe?.ingredients)
        val customAdapterMethod = MethodAdapter(recipe?.method)
        with(recipeBinding) {
            rvIngredients.adapter = customAdapterIngredient
            rvMethod.adapter = customAdapterMethod
        }
        val recyclerViewIngredient = view.findViewById<RecyclerView>(R.id.rvIngredients)
        val recyclerViewMethod = view.findViewById<RecyclerView>(R.id.rvMethod)
        val divider =
            context?.let { MaterialDividerItemDecoration(it, LinearLayoutManager.VERTICAL) }
        divider?.dividerInsetStart = 12
        divider?.dividerInsetEnd = 12
        divider?.isLastItemDecorated = false
        if (divider != null) {
            recyclerViewIngredient?.addItemDecoration(divider)
            recyclerViewMethod?.addItemDecoration(divider)
        }
    }

    private fun initUi() {
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