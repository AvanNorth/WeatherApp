package com.example.weatherapp.data

import com.example.weatherapp.data.api.GeoCodeApi
import com.example.weatherapp.data.api.mapper.CityMapper
import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.repository.GeoRepository
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val api: GeoCodeApi,
    private val cityMapper: CityMapper
) : GeoRepository {
    override suspend fun getCityFromCoords(lat: Double, lon: Double): City {
        return cityMapper.toCity(api.getCity(lat, lon))
    }
}