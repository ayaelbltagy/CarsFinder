package com.example.carfinder

import com.example.carfinder.presentation.layer.viewModel.CarsViewModel
import com.example.domain.domain.entity.ModelResponse
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class ViewModelTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()


    @Mock
    private var viewModel = mockk<CarsViewModel>(relaxed = true)


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.resetMain()

    }
    val carsFlow = flowOf(
        listOf(
            ModelResponse(
                id = 0,
                model = 1,
                plate_number = "ABC",
                brand = "BM",
                unit_price = 90.8,
                currency = "",
                color = "",
            ),
            ModelResponse(
                id = 1,
                model = 2,
                plate_number = "test",
                brand = "opel",
                unit_price = 78.9,
                currency = "",
                color = "",
            )
        )
    )

    @Test
    fun `should not empty list of flow`() = runBlockingTest {
        val result = MutableStateFlow(carsFlow)
        assertEquals(false, result.value.equals(""))
    }

    @Test
    fun `testing list not equals the real list`() = runBlockingTest {
        val result = MutableStateFlow(carsFlow)
        assertEquals(false, result.value.equals(viewModel.getList()))
    }

    @Test
    fun `testing search txt`() = runBlockingTest {
        val result = MutableStateFlow("search")
        viewModel.searchText = result.asStateFlow()
        assertEquals(false, viewModel.searchText.equals(""))
    }




}