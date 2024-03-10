package com.example.carfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carfinder.presentation.layer.theme.CarFinderTheme
import com.example.carfinder.presentation.layer.ui.NavigationPages
import com.example.carfinder.presentation.layer.viewModel.CarsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarFinderTheme {
                val viewModel = viewModel<CarsViewModel>()
                NavigationPages(viewModel)

            }
        }

    }
}


