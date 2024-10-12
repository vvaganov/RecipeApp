package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.model.Category
import kotlinx.serialization.json.Json.Default.decodeFromString
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val navOption = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        .build()

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name} ")

        val thread = Thread {
            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name} ")
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val responseBodyString = connection.inputStream.bufferedReader().readText()
            Log.i("!!!", "Body: $responseBodyString")

            val listCategory = decodeFromString<List<Category>>(responseBodyString)

            Log.i("!!!", "CategoryList: $listCategory")

        }
        thread.start()

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