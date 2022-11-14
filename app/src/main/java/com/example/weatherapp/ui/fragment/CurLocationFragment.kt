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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FragmentCurLocationBinding
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.ui.home.HomeViewModel
import com.example.weatherapp.ui.viewmodel.CurLocationWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import timber.log.Timber

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
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        binding = FragmentCurLocationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
        getLocation()
    }

    private fun initCurrentWeatherData(weather: Weather) {
        with(binding){
            tempTV.text = weather.temp.toString()
        }
    }

    private fun initWeekWeatherData() {
        val weatherList: RecyclerView = binding.weekTempList
        weatherList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                initCurrentWeatherData(it)
            },
                onFailure = { error ->
                    Timber.e(error.message)
                }
            )
        }
    }

    private fun getLocation() {
        lifecycleScope.launch {
            try {
                permissionsCheck()
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            lat = location.latitude
                            lon = location.longitude
                        }
                    }
            } catch (e: SecurityException) {
                Timber.e(e.message.toString())
            }
        }
        viewModel.getWeatherByCoords(lat, lon)
    }

    private fun permissionsCheck() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "Error: permission not granted",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            return
        }
    }
}