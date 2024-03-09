package com.example.carfinder.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carfinder.reponse.ModelResponse
import com.example.carfinder.viewModel.SearchViewModel
import com.google.gson.Gson

@Composable
fun navigatePage() {
    val navHostController = rememberNavController()
    val viewModel = SearchViewModel(LocalContext.current)
    NavHost(
        navController = navHostController,
        startDestination = "sample_data"
    ) {
        composable("sample_data") {

            SearchScreen(
                navController = navHostController,
                viewModel = viewModel
            )

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