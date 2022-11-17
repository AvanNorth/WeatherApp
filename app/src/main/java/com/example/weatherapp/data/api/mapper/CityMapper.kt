package com.example.weatherapp.data.api.mapper

import com.example.weatherapp.data.api.model.CityResponse
import com.example.weatherapp.data.api.model.CityResponseItem
import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.entity.CityList

class CityMapper() {
    fun getFirstCity(response: CityResponse): City = City(
        cityName = response[0].local_names.ru,
        country = response[0].country,
        lat = response[0].lat,
        lon = response[0].lon
    )

    private fun getFirstCity(response: CityResponseItem): City = City(
        cityName = response.name,
        country = response.country,
        lat = response.lat,
        lon = response.lon
    )

    fun toCityList(response: CityResponse): CityList = CityList(
        list = response.map { city -> getFirstCity(city) }
    )
}