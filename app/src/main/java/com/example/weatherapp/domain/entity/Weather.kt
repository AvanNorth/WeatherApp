package com.example.weatherapp.domain.entity

data class Weather(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val temp: Double,
    val icon: String,
    val desc: String,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val windDir: String,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int,
)