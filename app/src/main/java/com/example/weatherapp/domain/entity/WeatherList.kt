package com.example.weatherapp.domain.entity

data class WeatherList(
    val list: MutableMap<City, Weather>,
    val forecastList: MutableMap<City, WeatherForecast>
){
    constructor(): this(
        list = mutableMapOf<City, Weather>(),
        forecastList = mutableMapOf<City, WeatherForecast>()
    )
}