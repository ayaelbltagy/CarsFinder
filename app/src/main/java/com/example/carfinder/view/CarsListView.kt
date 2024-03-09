package com.example.carfinder.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.carfinder.R
import com.google.gson.Gson
import com.example.carfinder.reponse.ModelResponse
import com.example.carfinder.reponse.ResponseError
import com.example.carfinder.ui.theme.Purple80
import com.example.carfinder.viewModel.SearchViewModel
import com.google.gson.reflect.TypeToken


@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel) {

    val searchResults by viewModel.getSearchResult().collectAsState()

    SearchScreen(
            navController = navController,
            searchQuery = viewModel.searchQuery,
            searchResults = searchResults,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
     )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    searchQuery: String,
    searchResults: List<ModelResponse>,
    onSearchQueryChange: (String) -> Unit
) {
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
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
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
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                    else{
                        keyboardController?.hide()
                    }
                },
                content = {},
                active = true,
                onActiveChange = {},
                tonalElevation = 0.dp
            )
        }
        if (searchResults.isEmpty() && searchQuery.isNotEmpty()) {
            emptyState()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
            ) {
                items(
                    count = searchResults.size,
                    key = { index -> searchResults[index].id },
                    itemContent = { index ->
                        val car = searchResults[index]
                        CarListItem(car = car, navController = navController)
                    }
                )
            }
        }
    }
}

@Composable
fun CarListItem(
    navController: NavController,
    car: ModelResponse
) {
    Card(
        modifier = Modifier
            .clickable {
                val itemVal = Gson().toJson(car)
                navController.navigate("details/${itemVal}")
            }
            .padding(5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painterResource(R.drawable.honda),
                contentDescription = "Image",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .padding(5.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = car.brand + " " + car.color,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Price" + " " + car.unit_price,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}
@OptIn( ExperimentalComposeUiApi::class)
@Composable
fun emptyState(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val jsonString = readJsonFromAssets(LocalContext.current, "Error.json")
    val error = parseJsonToModel(jsonString)
    keyboardController?.hide()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = error!!.status.message,
            style = MaterialTheme.typography.titleSmall
        )

    }
}

private fun readJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

private fun parseJsonToModel(jsonString: String): ResponseError {
    val gson = Gson()
    return gson.fromJson(jsonString, object : TypeToken<ResponseError>() {}.type)
}
