package com.example.domain.domain.useCase

import com.example.domain.domain.repo.CarsRepository

class CarsUseCase(private val repository: CarsRepository) {
    suspend operator fun invoke() = repository.getCarsFromLocal()
}