package com.example.recipeapp

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.setPadding
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import java.io.InputStream
import kotlin.collections.MutableSet

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

            val isFavorite = getFavorites().toList().contains(recipe?.id.toString())

            if (isFavorite)
                ibFavorites.setImageResource(R.drawable.ic_heart)
            else
                ibFavorites.setImageResource(R.drawable.ic_heart_empty)

            ibFavorites.setOnClickListener {
                if (!getFavorites().toList().contains(recipe?.id.toString())) {
                    ibFavorites.setImageResource(R.drawable.ic_heart)
                    val favoriteSet = getFavorites()
                    val newSet = favoriteSet.plus(recipe?.id.toString())
                    setFavorites(newSet)
                } else {
                    ibFavorites.setImageResource(R.drawable.ic_heart_empty)
                    val favoriteSet = getFavorites()
                    val newSet = favoriteSet.minus(recipe?.id.toString())
                    setFavorites(newSet)
                }
            }
        }
    }

    private fun setFavorites(setFavoriteId: Set<String>?) {
        val sharedPref =
            activity?.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
                ?: return
        sharedPref.edit()?.putStringSet(PREF_FAVORITE_KEY, setFavoriteId)?.apply()
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPref =
            activity?.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val setId = sharedPref?.getStringSet(PREF_FAVORITE_KEY, emptySet())
        val mutableSteId = HashSet<String>(setId)
        return mutableSteId
    }
}