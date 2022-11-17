package com.example.weatherapp.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapter.WeatherAdapter
import com.example.weatherapp.databinding.FragmentCurLocationBinding
import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.domain.entity.WeatherForecast
import com.example.weatherapp.ui.utils.ContextExtensions.visible
import com.example.weatherapp.ui.viewmodel.CurLocationWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CurLocationFragment : Fragment() {

    private lateinit var binding: FragmentCurLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: CurLocationWeatherViewModel by viewModels()

    private var lat = 57.0
    private var lon = -2.15

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurLocationBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        initObservers()
        getLocation()
    }

    private fun initCurrentWeatherData(weather: Weather) {
        with(binding) {
            skyTV.text = weather.wx_desc
            tempFeelTV.text = getString(R.string.feel_like_text, weather.feelsLike)
            tempTV.text = getString(R.string.temp_text, weather.temp)
            windTV.text = getString(R.string.wind_spd_text, weather.windSpeed)
            windDirTV.text = getString(R.string.wind_dir_text, weather.windDir)
            humTV.text = getString(R.string.humidity_text, weather.humidity)
            visTV.text = getString(R.string.visibility_text, weather.vis)

            Picasso.get()
                .load(
                    "http://www.weatherunlocked.com/Images/icons/2/${
                        weather.icon.replace(
                            ".gif",
                            ".png"
                        )
                    }"
                ).fit()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.wxIcoIV, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception?) {
                        Timber.e("Picasso", e?.message.toString())
                    }
                })

            hideLoadingBar()
        }
    }

    private fun initCityData(city: City) {
        with(binding) {
            cityTV.text = city.cityName
            countryTV.text = city.country
        }
    }

    private fun initWeekWeatherData(forecast: WeatherForecast) {
        val weatherList: RecyclerView = binding.weekTempList
        weatherList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        weatherList.adapter = WeatherAdapter(forecast, requireContext())
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                initCurrentWeatherData(it)
                viewModel.getCity(lat, lon)
                viewModel.getWeekWeatherByCoords(lat, lon)
            },
                onFailure = { error ->
                    Timber.e(error.message)
                }
            )
        }

        viewModel.city.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                initCityData(it)
            },
                onFailure = { error ->
                    Timber.e(error.message)
                }
            )
        }

        viewModel.weatherForecast.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                initWeekWeatherData(it)
            },
                onFailure = { error ->
                    Timber.e(error.message)
                }
            )
        }
    }

    private fun getLocation() {
        showLoadingBar()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activityResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        lat = location.latitude
                        lon = location.longitude
                        viewModel.getWeatherByCoords(lat, lon)
                    } else {
                        Snackbar.make(
                            binding.root,
                            "error getting location",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun hideLoadingBar() {
        binding.curLocPB.visible(false)
        binding.curLocationContainer.visible(true)
    }

    private fun showLoadingBar() {
        binding.curLocationContainer.visible(false)
        binding.curLocPB.visible(true)
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            permissions.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            if (location != null) {
                                lat = location.latitude
                                lon = location.longitude
                                viewModel.getWeatherByCoords(lat, lon)
                            } else {
                                Snackbar.make(
                                    binding.root,
                                    "error getting location",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.perm_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
}
