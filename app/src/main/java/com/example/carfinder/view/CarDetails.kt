package com.example.carfinder.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfinder.R
import androidx.compose.material3.Text
import com.example.carfinder.reponse.ModelResponse
import com.example.carfinder.ui.theme.Purple80

@Composable
fun CarDataDetails(data: ModelResponse) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Purple80),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Car Details",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Image(
            painterResource(R.drawable.honda),
            contentDescription = "Image",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Text(
            text = data.brand,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = data.plate_number,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = data.color,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = "Price"+" "+":"+" "+data.unit_price,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal)
    }

}