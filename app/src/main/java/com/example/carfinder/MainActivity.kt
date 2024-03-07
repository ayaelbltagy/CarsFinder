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
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.ktx.R
 import com.example.carfinder.view.SearchScreen
import com.example.carfinder.viewModel.SearchViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // CarFinderTheme {
            // navigatePage()
            // }
                 SearchScreen(viewModel = SearchViewModel(this))

        }
    }
}




//@Composable
//fun navigatePage() {
//    val navHostController = rememberNavController()
//
//    NavHost(
//        navController = navHostController,
//        startDestination = "sample_data"
//    ) {
//        composable("sample_data") {
//            CarList(navController = navHostController)
//        }
//        composable("details/{item}",
//            arguments = listOf(
//                navArgument("item") {
//                    type = NavType.StringType
//                }
//            )
//        ) { backStackEntry ->
//            backStackEntry?.arguments?.getString("item")?.let { json ->
//                val item = Gson().fromJson(json, ModelResponse::class.java)
//                CarDataDetails(data = item)
//            }
//        }
//    }
//}