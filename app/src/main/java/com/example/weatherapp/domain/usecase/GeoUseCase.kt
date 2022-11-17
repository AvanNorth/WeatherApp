package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.entity.CityList
import com.example.weatherapp.domain.repository.GeoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeoUseCase @Inject constructor(
    private val geoRepository: GeoRepository
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getCity(lat: Double, lon: Double): City {
        return withContext(dispatcher) {
            geoRepository.getCityFromCoords(lat, lon)
        }
    }

    suspend fun getCity(name: String): CityList {
        return withContext(dispatcher) {
            geoRepository.getCityFromName(name)
        }
    }
}