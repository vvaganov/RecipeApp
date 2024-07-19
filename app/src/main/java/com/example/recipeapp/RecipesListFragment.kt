package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import java.io.InputStream

class RecipesListFragment : Fragment() {

    private val recipesListBinding:
            FragmentRecipesListBinding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }

    private var categoryId: Int? = null
    private var title: String? = null
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return recipesListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        with(recipesListBinding) {
            recipesListBinding.tvRecipeList.text = title
            try {
                val inputStream: InputStream? =
                    context?.assets?.open("$imageUrl")
                val drawable = Drawable.createFromStream(inputStream, null)
                imgRecipeList.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.e("!!!", e.stackTrace.toString())
            }
        }
        initRecycler()
    }

    private fun initBundleData() {
        arguments.let {
            categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
            title = requireArguments().getString(ARG_CATEGORY_NAME)
            imageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)
        }
    }

    private fun initRecycler() {
        val recipeList = getRecipesByCategoryId(categoryId)
        val customAdapter = RecipeListAdapter(recipeList)
        recipesListBinding.rvRecipeList.adapter = customAdapter
        customAdapter.setOnItemClickListener(
            object : RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId:Int) {
                    openRecipeByRecipeId(recipeId)
                }
            }
        )
    }

    private fun getRecipesByCategoryId(categoryId: Int?): List<Recipe> {
        val listRecipe = STUB.getRecipe()
        return if (categoryId == 0) listRecipe else emptyList()
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer)
        }
    }
}