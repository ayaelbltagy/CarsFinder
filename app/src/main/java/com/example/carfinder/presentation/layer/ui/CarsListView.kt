package com.example.carfinder.presentation.layer.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carfinder.R
import com.google.gson.Gson
import com.example.carfinder.domain.ResponseError
import com.example.domain.domain.entity.ModelResponse
import com.google.gson.reflect.TypeToken


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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun emptyState(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val jsonString = getJsonDataFromAsset(LocalContext.current, "Error.json")
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


private fun getJsonDataFromAsset(context: Context, data: String): String {
    return context.assets.open(data).bufferedReader().use { it.readText() }
}


private fun parseJsonToModel(jsonString: String): ResponseError {
    val gson = Gson()
    return gson.fromJson(jsonString, object : TypeToken<ResponseError>() {}.type)
}
