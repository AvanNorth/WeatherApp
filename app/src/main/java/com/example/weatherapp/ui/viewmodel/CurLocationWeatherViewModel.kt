package com.example.weatherapp.ui.viewmodel;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.WeatherUseCase
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.launch

@HiltViewModel
class CurLocationWeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    //private var _weatherList: MutableLiveData<Result<>>

    fun getWeatherByCoords(lat: Double, lon: Double) = viewModelScope.launch {
        try {
            _weather.value = Result.success(weatherUseCase.getWeatherByCoords(lat, lon))
        } catch (ex: Exception) {
            _weather.value = Result.failure(ex)
        }
    }

    fun getWeekWeatherByCoords(lat: Double, lon: Double) = viewModelScope.launch {

    }
}
