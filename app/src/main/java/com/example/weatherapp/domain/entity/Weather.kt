package com.example.weatherapp.domain.entity

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val temp: Double,
    val icon: String,
    val desc: String,
    val feelsLike: Double,
    val humidity: Double,
    val windSpeed: Double,
    val windDir: String,
    val wx_desc: String,
    val vis: Double,
    val devPoint: Double,
    val slpMb: Double
)