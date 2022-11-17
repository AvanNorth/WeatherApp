package com.example.weatherapp.data

import com.example.weatherapp.data.api.GeoCodeApi
import com.example.weatherapp.data.api.mapper.CityMapper
import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.entity.CityList
import com.example.weatherapp.domain.repository.GeoRepository
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val api: GeoCodeApi,
    private val cityMapper: CityMapper
) : GeoRepository {
    override suspend fun getCityFromCoords(lat: Double, lon: Double): City {
        return cityMapper.getFirstCity(api.getCity(lat, lon))
    }

    override suspend fun getCityFromName(name: String): CityList {
        return cityMapper.toCityList(api.getCity(name))
    }
}