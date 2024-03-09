package com.example.carfinder.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carfinder.reponse.ModelResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(context: Context) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set


    private val dataFileString = getJsonDataFromAsset(context, "Data.json")
    private val gson = Gson()
    private val listSampleType = object : TypeToken<List<ModelResponse>>() {}.type
    private var sampleData: List<ModelResponse> = gson.fromJson(dataFileString, listSampleType)
    private val carsFlow = flowOf(sampleData)


    fun getSearchResult() : StateFlow<List<ModelResponse>>{
        lateinit var searchResults: StateFlow<List<ModelResponse>>
        viewModelScope.launch{
              searchResults =
                snapshotFlow { searchQuery }
                    .combine(carsFlow) { searchQuery, cars ->
                        when {
                            searchQuery.isNotEmpty() -> cars.filter { car ->
                                car.color.contains(searchQuery, ignoreCase = true) || car.unit_price.contains(searchQuery, ignoreCase = true)

                            }

                            else -> cars

                        }
                    }.stateIn(
                        scope = viewModelScope,
                        initialValue = emptyList(),
                        started = SharingStarted.WhileSubscribed(5000)
                    )
        }
        return searchResults
    }


    private fun getJsonDataFromAsset(context: Context, data: String): String {
        return context.assets.open(data).bufferedReader().use { it.readText() }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        Log.i("tagMe", newQuery)
    }

}
