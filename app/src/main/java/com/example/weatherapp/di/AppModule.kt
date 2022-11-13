package com.example.weatherapp.di

import com.example.weatherapp.data.WeatherRepositoryImpl
import com.example.weatherapp.data.api.Api
import com.example.weatherapp.data.api.mapper.WeatherMapper
import com.example.weatherapp.domain.conventer.WindConverter
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GetNearCitiesUseCase
import com.example.weatherapp.domain.usecase.GetWeatherByIdUseCase
import com.example.weatherapp.domain.usecase.GetWeatherByNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherMapper(): WeatherMapper = WeatherMapper(
        windConverter = WindConverter()
    )

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: Api,
        weatherMapper: WeatherMapper
    ): WeatherRepository = WeatherRepositoryImpl(
        api = api,
        weatherMapper = weatherMapper
    )

    @Provides
    @Singleton
    fun provideNearCitiesUseCase(
        weatherRepository: WeatherRepository
    ): GetNearCitiesUseCase = GetNearCitiesUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    @Provides
    @Singleton
    fun provideWeatherByIdUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherByIdUseCase = GetWeatherByIdUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    @Provides
    @Singleton
    fun getWeatherByNameUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherByNameUseCase = GetWeatherByNameUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )
}