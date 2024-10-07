package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val navOption = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .build()

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCategory.setOnClickListener {
            findNavController(R.id.my_nav_host_fragment).navigate(
                R.id.categoriesListFragment,
                null,
                navOption
            )
        }

        binding.btnFavourites.setOnClickListener {
            findNavController(R.id.my_nav_host_fragment).navigate(
                R.id.favoritesFragment,
                null,
                navOption
            )
        }
    }
}