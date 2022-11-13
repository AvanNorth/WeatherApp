package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entity.Cities
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.usecase.GetNearCitiesUseCase
import com.example.weatherapp.domain.usecase.GetWeatherByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase,
    private val getNearCitiesUseCase: GetNearCitiesUseCase,
) : ViewModel() {

    private var _cities: MutableLiveData<Result<Cities>> = MutableLiveData()
    val cities: LiveData<Result<Cities>> = _cities

    fun getNearCities(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val cities = getNearCitiesUseCase(latitude, longitude, 10)
                _cities.value = Result.success(cities)
            } catch (ex: Exception) {
                _cities.value = Result.failure(ex)
            }
        }
    }

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    fun getWeatherByName(cityName: String) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByNameUseCase(cityName)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
            }
        }
    }
}