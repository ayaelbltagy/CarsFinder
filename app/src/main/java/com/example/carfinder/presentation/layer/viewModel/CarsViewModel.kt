package com.example.carfinder.presentation.layer.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domain.entity.ModelResponse
import com.example.domain.domain.useCase.CarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
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
class CarsViewModel @Inject constructor(private val useCase: CarsUseCase) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()


    init {
        getList()
    }

    fun getList(): List<ModelResponse> {
        var list = emptyList<ModelResponse>()
        viewModelScope.launch {
            try {
                list = useCase()
            } catch (exception: Exception) {
            }
        }
        Log.i("list", list.size.toString())
        return list
    }

    private val _cars = MutableStateFlow(getList())

    var cars = searchText
        .debounce(500L)
        .onEach { }
        .combine(_cars) { text, response ->
            if (text.isBlank()) {
                response
            } else {
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
}
