package com.example.carfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.carfinder.presentation.layer.theme.CarFinderTheme
import com.example.carfinder.presentation.layer.ui.NavigationPages
import com.example.carfinder.presentation.layer.viewModel.CarsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CarsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarFinderTheme {
                NavigationPages(viewModel)

            }
        }

    }
}


