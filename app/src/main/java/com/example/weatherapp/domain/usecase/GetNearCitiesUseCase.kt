package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.entity.Cities
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNearCitiesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        count: Int
    ): Cities {
        return withContext(dispatcher) {
            weatherRepository.getNearWeather(
                latitude,
                longitude,
                count
            )
        }
    }
}