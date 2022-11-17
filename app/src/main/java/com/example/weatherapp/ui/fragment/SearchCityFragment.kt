package com.example.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.SearchCityAdapter
import com.example.weatherapp.databinding.FragmentSearchCityBinding
import com.example.weatherapp.domain.entity.*
import com.example.weatherapp.ui.utils.ContextExtensions.visible
import com.example.weatherapp.ui.viewmodel.SearchWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SearchCityFragment : Fragment() {

    private lateinit var binding: FragmentSearchCityBinding
    private val viewModel: SearchWeatherViewModel by viewModels()

    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private var cursor: Int = 0
    private lateinit var cityList: CityList
    private var weatherList = WeatherList()
    private lateinit var curWeather: Weather

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchCityBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initSearchView()
    }

    private fun initSearchView() {
        binding.searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                weatherList = WeatherList()
                cursor = 0
                viewModel.getCityList(query)
                showLoadingBar()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun initWeatherData(weatherList: WeatherList) {
        with(binding) {
            val adapter = SearchCityAdapter(weatherList, requireContext())

            searchList.layoutManager = LinearLayoutManager(requireContext())
            searchList.adapter = adapter

            hideLoadingBar()
        }
    }

    /*
       Пока не дойдем до конца списка городов, получаем погоду для каждого и
        заносим в переменную weatherList. На каждой итерации двигаем курсор и
        запрашиваем погоду для следующего города.
       После того как наш список погоды готов, показываем его
    **/
    private fun reduce(weatherForecats: WeatherForecast) {
        if (this::cityList.isInitialized) {
            if (cursor != cityList.list.size - 1) {
                weatherList.list[cityList.list[cursor]] = curWeather
                weatherList.forecastList[cityList.list[cursor]] = weatherForecats
                cursor++
                viewModel.getWeatherByCoords(cityList.list[cursor].lat, cityList.list[cursor].lon)
            }
            /* В случае, если у нас всего 1 город в списке, добавляем его погоду и показываем список погоды
        * */
            else if (cityList.list.size == 1 && cursor == 0) {
                weatherList.list[cityList.list[cursor]] = curWeather
                weatherList.forecastList[cityList.list[cursor]] = weatherForecats
                initWeatherData(weatherList)
            } else
                initWeatherData(weatherList)
        }
    }

    private fun initObservers() {
        /* Получив погоду для определенного города, отправляем ее на добавление в лист погоды
        * */
        viewModel.weather.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                curWeather = it
                viewModel.getWeekWeatherByCoords(cityList.list[cursor].lat, cityList.list[cursor].lon)
            },
                onFailure = { error ->
                    Timber.e(error.message)
                }
            )
        }

        viewModel.weatherForecast.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                reduce(it)
            },
                onFailure = { error ->
                    Timber.e(error.message)
                }
            )
        }

        /*
        Как только получили список городов, сохраняем его и запускаем процесс получения погоды для всего списка,
         начиная с 0 элемента.
        * */
        viewModel.cityList.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                cityList = it
                viewModel.getWeatherByCoords(it.list[0].lat, it.list[0].lon)
            }, onFailure = { error ->
                Timber.e(error.message)
            })
        }
    }

    private fun hideLoadingBar() {
        binding.searchPB.visible(false)
        binding.hintTV.visible(false)
        binding.searchContainer.visible(true)
    }

    private fun showLoadingBar() {
        binding.searchContainer.visible(false)
        binding.hintTV.visible(false)
        binding.searchPB.visible(true)
    }
}