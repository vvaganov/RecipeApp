package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class FavoritesFragment : Fragment() {

    private var _favoriteFragmentBinding: FragmentFavoritesBinding? = null
    private val favoriteFragmentBinding get() = _favoriteFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _favoriteFragmentBinding = FragmentFavoritesBinding.inflate(inflater,container,false)
        val view  = favoriteFragmentBinding.root
        return view
    }
}
