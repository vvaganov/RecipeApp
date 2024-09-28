package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipeapp.ARG_RECIPE_ID
import com.example.recipeapp.R
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels()

    private val recipeBinding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private val recipeId: Int
        get() = arguments?.getInt(ARG_RECIPE_ID)
            ?: throw IllegalArgumentException("argument is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return recipeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

        viewModel.loadRecipe(recipeId)

        val seekBar: SeekBar = recipeBinding.sbNumberOfServings
        seekBar.setPadding(resources.getDimensionPixelSize(R.dimen.indent_0))
        seekBar.setOnSeekBarChangeListener(
            PortionSeekBarListener(onChangeIngredients = { viewModel.changeNumberOfServing(it) })
        )

        val customAdapterIngredient = IngredientsAdapter(STUB.getRecipe().flatMap { it.ingredients })
        val customAdapterMethod = MethodAdapter(STUB.getRecipe().flatMap { it.method })

        recipeBinding.rvIngredients.adapter = customAdapterIngredient
        recipeBinding.rvMethod.adapter = customAdapterMethod

        viewModel.recipeState.observe(viewLifecycleOwner) { state ->

            customAdapterIngredient.updateIngredients(state.numberServings)
            customAdapterIngredient.dataSet = state.recipe?.ingredients
            customAdapterMethod.dataSet = state.recipe?.method

            with(recipeBinding) {

                tvRecipeTitle.text = state.recipe?.title

                imgRecipe.setImageDrawable(state?.recipeImage)

                tvNumberOfServings.text = state?.numberServings.toString()

                if (state?.isFavorites == true)
                    ibFavorites.setImageResource(R.drawable.ic_heart)
                else
                    ibFavorites.setImageResource(R.drawable.ic_heart_empty)
            }
        }
        recipeBinding.ibFavorites.setOnClickListener {
            viewModel.onFavoritesClicked(recipeId)
        }
        setPaddingIngredientListLayout()
        setDivider()
    }

    private fun setPaddingIngredientListLayout() {
        val ingredientListLeanerLayout = recipeBinding.llIngredientList
        val paddingSizeDp = resources.getDimensionPixelSize(R.dimen.indent_16)
        ingredientListLeanerLayout.setPaddingRelative(paddingSizeDp, 0, paddingSizeDp, 0)
    }

    private fun setDivider() {
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

}

class PortionSeekBarListener(
    val onChangeIngredients: (Int) -> Unit
) : SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onChangeIngredients(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
}