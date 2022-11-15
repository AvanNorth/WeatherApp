package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.entity.WeatherForecast
import com.example.weatherapp.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeather(cityName: String): Weather
    suspend fun getWeather(lat: Double, lon: Double): Weather
    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherForecast
}