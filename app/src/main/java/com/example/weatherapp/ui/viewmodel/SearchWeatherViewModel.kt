package com.example.weatherapp.ui.viewmodel;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.entity.CityList
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.entity.WeatherForecast
import com.example.weatherapp.domain.usecase.GeoUseCase
import com.example.weatherapp.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val geoUseCase: GeoUseCase
) : ViewModel() {

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    private var _city: MutableLiveData<Result<City>> = MutableLiveData()
    val city: LiveData<Result<City>> = _city

    private var _cityList: MutableLiveData<Result<CityList>> = MutableLiveData()
    val cityList: LiveData<Result<CityList>> = _cityList

    private var _weatherForecast: MutableLiveData<Result<WeatherForecast>> = MutableLiveData()
    val weatherForecast: LiveData<Result<WeatherForecast>> = _weatherForecast

    fun getWeatherByCoords(lat: Double, lon: Double) = viewModelScope.launch {
        try {
            _weather.value = Result.success(weatherUseCase.getWeatherByCoords(lat, lon))
        } catch (ex: Exception) {
            _weather.value = Result.failure(ex)
        }
    }

    fun getWeekWeatherByCoords(lat: Double, lon: Double) = viewModelScope.launch {
        try {
            _weatherForecast.value = Result.success(weatherUseCase.getWeatherForecast(lat, lon))
        } catch (ex: Exception) {
            _weatherForecast.value  = Result.failure(ex)
        }
    }

    fun getCityList(name: String) = viewModelScope.launch {
        try {
            _cityList.value = Result.success(geoUseCase.getCity(name))
        } catch (ex: Exception) {
            _cityList.value = Result.failure(ex)
        }
    }
}
