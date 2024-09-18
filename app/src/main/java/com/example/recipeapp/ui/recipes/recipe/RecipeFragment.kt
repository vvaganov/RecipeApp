package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import com.example.recipeapp.ARG_RECIPE_ID
import com.example.recipeapp.R
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import java.io.InputStream

class RecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels()

    private val recipeBinding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private var recipeId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return recipeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecipeIdArguments()
        initUI()
    }

    private fun initRecipeIdArguments() {
        recipeId = arguments?.getInt(ARG_RECIPE_ID)
    }

    private fun initRecycler(
        customAdapterIngredient: IngredientsAdapter,
        customAdapterMethod: MethodAdapter
    ) {
        with(recipeBinding) {
            rvIngredients.adapter = customAdapterIngredient
            rvMethod.adapter = customAdapterMethod
        }
        val seekBar = recipeBinding.sbNumberOfServings

        seekBar.setPadding(resources.getDimensionPixelSize(R.dimen.indent_0))
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    customAdapterIngredient.notifyDataSetChanged()
                    customAdapterIngredient.updateIngredients(progress)
                    recipeBinding.tvNumberOfServings.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            }
        )
        val recyclerViewIngredient = recipeBinding.rvIngredients
        val recyclerViewMethod = recipeBinding.rvMethod
        val ingredientListLeanerLayout = recipeBinding.llIngredientList
        val paddingSizeDp = resources.getDimensionPixelSize(R.dimen.indent_16)
        ingredientListLeanerLayout.setPaddingRelative(paddingSizeDp, 0, paddingSizeDp, 0)
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

        viewModel.loadRecipe(recipeId)

        viewModel.recipeState.observe(viewLifecycleOwner) { state ->
            val recipe = state?.recipe
            val customAdapterIngredient = IngredientsAdapter(recipe?.ingredients)
            val customAdapterMethod = MethodAdapter(recipe?.method)
            initRecycler(customAdapterIngredient, customAdapterMethod)
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

                if (state?.isFavorites == true)
                    ibFavorites.setImageResource(R.drawable.ic_heart)
                else
                    ibFavorites.setImageResource(R.drawable.ic_heart_empty)
                ibFavorites.setOnClickListener {
                    viewModel.onFavoritesClicked(recipe?.id)
                }
            }
        }
    }
}