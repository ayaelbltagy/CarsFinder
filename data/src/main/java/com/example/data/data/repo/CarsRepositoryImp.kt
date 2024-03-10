package com.example.data.data.repo

import com.example.domain.domain.entity.ModelResponse
import com.example.domain.domain.repo.CarsRepository


class CarsRepositoryImp constructor(private val db: List<ModelResponse> ) : CarsRepository {
    override suspend fun getCarsFromLocal(): List<ModelResponse> = db

}