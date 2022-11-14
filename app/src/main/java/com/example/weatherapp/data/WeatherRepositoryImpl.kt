package com.example.weatherapp.data

import com.example.weatherapp.data.api.Api
import com.example.weatherapp.data.api.mapper.WeatherMapper
import com.example.weatherapp.domain.entity.Cities
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val weatherMapper: WeatherMapper
) : WeatherRepository {

    override suspend fun getWeather(cityName: String): Weather {
        return weatherMapper.toWeather(api.getWeather(cityName))
    }

    override suspend fun getWeather(cityId: Int): Weather {
        return weatherMapper.toWeather(api.getWeather(cityId))
    }

    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        return weatherMapper.toWeather(api.getWeather(lat, lon))
    }

    override suspend fun getNearWeather(latitude: Double, longitude: Double, count: Int): Cities {
        return weatherMapper.toListWeather(api.getNearCity(latitude, longitude, count))
    }
}