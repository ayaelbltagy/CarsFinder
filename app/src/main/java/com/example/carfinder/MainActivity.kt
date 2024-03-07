package com.example.carfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.example.carfinder.reponse.ModelResponse
import com.example.carfinder.ui.theme.CarFinderTheme
import com.example.carfinder.view.CarDataDetails
import androidx.compose.ui.platform.LocalContext
import com.example.carfinder.view.SearchScreen
import com.example.carfinder.viewModel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarFinderTheme {
                navigatePage()

            }
        }
    }


}



@Composable
fun navigatePage() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = "sample_data"
    ) {
        composable("sample_data") {
            val context = LocalContext.current
            SearchScreen(navController = navHostController, viewModel = SearchViewModel(context))

        }
        composable("details/{item}",
            arguments = listOf(
                navArgument("item") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry?.arguments?.getString("item")?.let { json ->
                val item = Gson().fromJson(json, ModelResponse::class.java)
                CarDataDetails(data = item)
            }
        }
    }
}