package com.example.weatherapp.domain.entity

data class DayWeather(
    val minTemp: Double,
    val maxTemp: Double,
    val date: String,
    val windSpdMax: Double,
    val sunriseTime: String,
    val moonriseTime: String
)
