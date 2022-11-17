package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.entity.CityList

interface GeoRepository {
    suspend fun getCityFromCoords(lat: Double, lon: Double): City
    suspend fun getCityFromName(name: String): CityList
}