package com.example.carfinder.presentation.layer.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domain.entity.ModelResponse
import com.example.domain.domain.useCase.CarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class CarsViewModel @Inject constructor (private val useCase: CarsUseCase): ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val carsList = listOf(
        ModelResponse(
            1,
            2020,
            "EFG 321",
            "Kia",
            50.5,
            "EGP",
            "RED"
        ),
        ModelResponse(
            2,
            2020,
            "EFG 321",
            "Hyundai",
            34.5,
            "EGP",
            "BLUE"
        ),
    )

    init {
        getList()
    }

    fun getList(){
        viewModelScope.launch {
            Log.i("test", useCase().get(0).brand)
        }
    }

    private val _cars = MutableStateFlow(carsList)
    val cars = searchText
        .debounce(500L)
        .onEach { }
        .combine(_cars) { text, response ->
            if (text.isBlank()) {
                response
            } else {
                delay(1000L)
                response.filter {
                    it.color.contains(text, ignoreCase = true) ||
                            it.unit_price.toString().contains(text, ignoreCase = true)

                }

            }
        }
        .onEach { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _cars.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    //private val dataFileString = getJsonDataFromAsset(context, "Data.json")
//private val gson = Gson()
//private val listSampleType = object : TypeToken<List<ModelResponse>>() {}.type
//private var sampleData: List<ModelResponse> = gson.fromJson(dataFileString, listSampleType)
//private val carsFlow = flowOf(sampleData)

    private fun getJsonDataFromAsset(context: Context, data: String): String {
        return context.assets.open(data).bufferedReader().use { it.readText() }

    }
}
