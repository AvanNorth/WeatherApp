package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.entity.Cities
import com.example.weatherapp.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeather(cityName: String): Weather
    suspend fun getWeather(cityId: Int): Weather
    suspend fun getWeather(lat: Double, lon: Double): Weather
    suspend fun getNearWeather(latitude: Double, longitude: Double, count: Int): Cities
}