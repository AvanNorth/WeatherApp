package com.example.weatherapp.domain.entity

data class City(
    val cityName: String,
    val country: String,
    val lat: Double,
    val lon: Double
)