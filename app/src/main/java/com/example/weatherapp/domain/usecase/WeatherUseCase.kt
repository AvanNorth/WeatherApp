package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.entity.WeatherForecast
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    suspend fun getWeatherByName(cityName: String): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeather(cityName)
        }
    }

    suspend fun getWeatherByCoords(lat: Double, lon: Double): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeather(lat, lon)
        }
    }

    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherForecast {
        return withContext(dispatcher) {
            weatherRepository.getWeatherForecast(lat, lon)
        }
    }
}