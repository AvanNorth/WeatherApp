package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.entity.WeatherForecast
import com.example.weatherapp.domain.repository.GeoRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeoUseCase @Inject constructor(
    private val geoRepository: GeoRepository
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    suspend fun getCity(lat: Double, lon: Double): City {
        return withContext(dispatcher) {
           geoRepository.getCityFromCoords(lat, lon)
        }
    }
}