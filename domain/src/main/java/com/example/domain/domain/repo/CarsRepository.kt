package com.example.domain.domain.repo

import com.example.domain.domain.entity.ModelResponse

interface CarsRepository {
    suspend fun getCarsFromLocal(): List<ModelResponse>

}