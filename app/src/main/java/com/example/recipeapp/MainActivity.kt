package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.serialization.json.Json.Default.decodeFromString
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    private val navOption = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val thread = Thread {

            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()

           val response =  client.newCall(request).execute().body?.string()

            Log.i("!!!", "Response $response")
            val listCategory = decodeFromString<List<Category>>(response.toString()).map { it.id }

            listCategory.forEach {
                threadPool.execute {
                    val clientRecipe = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                    val requestRecipe = Request.Builder()
                        .url("https://recipes.androidsprint.ru/api/category/$it/recipes")
                        .build()
                    val responseRecipe = clientRecipe.newCall(requestRecipe).execute()
                    val responseRecipeBody = responseRecipe.body?.string()
                    val listRecipe = decodeFromString<List<Recipe>>(responseRecipeBody.toString())
                    Log.i("!!!", "ListRecipe : $listRecipe")
                }
            }
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