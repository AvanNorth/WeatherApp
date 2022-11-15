package com.example.weatherapp.data.api.mapper

import com.example.weatherapp.data.api.model.CityResponse
import com.example.weatherapp.domain.entity.City

class CityMapper() {
    fun toCity(response: CityResponse): City = City(
        cityName = response[0].local_names.ru,
        country = response[0].country
    )
}