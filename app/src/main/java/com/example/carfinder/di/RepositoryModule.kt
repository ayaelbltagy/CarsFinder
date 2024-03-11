package com.example.carfinder.di

import android.content.Context
import com.example.data.data.repo.CarsRepositoryImp
import com.example.domain.domain.entity.ModelResponse
import com.example.domain.domain.repo.CarsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCarsList(@ApplicationContext appContext: Context): List<ModelResponse> {
        val dataFileString = getJsonDataFromAsset(appContext, "Data.json")
        val gson = Gson()
        val listSampleType = object : TypeToken<List<ModelResponse>>() {}.type
        val response: List<ModelResponse> = gson.fromJson(dataFileString, listSampleType)
        return response
    }

    private fun getJsonDataFromAsset(context: Context, data: String): String {
        return context.assets.open(data).bufferedReader().use { it.readText() }
    }


    @Provides
    fun provideRepository(db: List<ModelResponse>): CarsRepository {
        return CarsRepositoryImp(db)
    }
}