package com.example.domain.domain.repo

import android.content.Context
import com.example.domain.domain.entity.ModelResponse

interface CarsRepository {
    suspend fun getCarsFromLocal(): List<ModelResponse>

}