package com.example.carfinder.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carfinder.reponse.ModelResponse
import com.example.carfinder.ui.theme.Purple80
import com.example.carfinder.viewModel.CarsViewModel
import com.google.gson.Gson

@Composable
fun NavigationPages(viewModel: CarsViewModel) {

    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = "sample_data"
    ) {
        composable("sample_data") {

            search(
                viewModel = viewModel,
                navController = navHostController
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun search(viewModel: CarsViewModel, navController: NavController) {
    val searchText by viewModel.searchText.collectAsState()
    val cars by viewModel.cars.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Purple80),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Car finder",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            SearchBar(
                query = searchText,
                onQueryChange = { viewModel.onSearchTextChange(it) },
                onSearch = { keyboardController?.hide() },
                placeholder = {
                    Text(text = "Search cars")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchTextChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Clear search"
                            )
                        }
                    } else {
                        keyboardController?.hide()
                    }
                },
                content = {},
                active = true,
                onActiveChange = {},
                tonalElevation = 0.dp
            )
        }
        if (cars.isEmpty() && searchText.isNotEmpty()) {
            emptyState()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
            ) {
                items(
                    count = cars.size,
                    key = { index -> cars[index].id },
                    itemContent = { index ->
                        val car = cars[index]
                        CarListItem(car = car, navController = navController)
                    }
                )
            }
        }
    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        TextField(
//            value = searchText,
//            onValueChange = viewModel::onSearchTextChange,
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = { Text(text = "Search") }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        if (isSearching) {
//            Box(modifier = Modifier.fillMaxSize()) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) {
//                items(
//                    count = cars.size,
//                    key = { index -> cars[index].id },
//                    itemContent = { index ->
//                        val car = cars[index]
//                        CarListItem(car = car, navController = navController)
//                    }
//                )
//            }
//        }
//    }

}