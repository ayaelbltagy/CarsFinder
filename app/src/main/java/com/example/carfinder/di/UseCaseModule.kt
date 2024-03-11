package com.example.carfinder.di

import com.example.domain.domain.repo.CarsRepository
import com.example.domain.domain.useCase.CarsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(repo: CarsRepository): CarsUseCase {
        return CarsUseCase(repo)
    }
}