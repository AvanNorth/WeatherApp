package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.entity.City

interface GeoRepository {
    suspend fun getCityFromCoords(lat: Double, lon: Double): City
}