package com.example.carfinder.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.carfinder.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.carfinder.reponse.ModelResponse
import com.example.carfinder.ui.theme.Purple80
import com.example.carfinder.viewModel.SearchViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    SearchScreen(
        searchQuery = viewModel.searchQuery,
        searchResults = searchResults,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: List<ModelResponse>,
    onSearchQueryChange: (String) -> Unit
) {
    Column {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
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
                onSearch = {},
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
                trailingIcon = {},
                content = {},
                active = true,
                onActiveChange = {},
                tonalElevation = 0.dp
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
        ) {
            items(
                count = searchResults.size,
                key = { index -> searchResults[index].model },
                itemContent = { index ->
                    val car = searchResults[index]
                    CarListItem(car = car)
                }
            )
        }

    }
}

@Composable
fun CarListItem(
    car: ModelResponse
) {
    Card(
        modifier = Modifier
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


//@Composable
//fun CarList(navController: NavController) {
//    val context = LocalContext.current
//    val dataFileString = getJsonDataFromAsset(context, "Data.json")
//    val gson = Gson()
//    val listSampleType = object : TypeToken<List<ModelResponse>>() {}.type
//    var sampleData: List<ModelResponse> = gson.fromJson(dataFileString, listSampleType)
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(50.dp)
//            .background(Purple80),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Car finder",
//            color = Color.White,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//
//
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth(),
//        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 50.dp)
//
//    ) {
//        items(sampleData) { data ->
//            CarDataListItem(data, navController)
//        }
//    }
//
//}
//@Composable
//fun CarDataListItem(data: ModelResponse, navController: NavController) {
//    Card(
//        modifier = Modifier
//            .clickable {
//                val itemVal = Gson().toJson(data)
//                navController.navigate("details/${itemVal}")
//            }
//            .padding(5.dp)
//            .fillMaxSize(),
//        shape = RoundedCornerShape(5.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Start,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Image(
//                painterResource(R.drawable.honda),
//                contentDescription = "Image",
//                modifier = Modifier
//                    .width(70.dp)
//                    .height(70.dp)
//                    .padding(5.dp)
//                    .clip(CircleShape)
//            )
//
//            Column(
//                modifier = Modifier.padding(10.dp)
//            ) {
//                Text(
//                    text = data.brand + " " + data.color,
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Text(
//                    text = "Price" + " " + data.unit_price,
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//        }
//    }
//}

//fun getJsonDataFromAsset(context: Context, data: String): String {
//    return context.assets.open(data).bufferedReader().use { it.readText() }
//}


