package com.example.weatherapp.di

import com.example.weatherapp.data.api.Api
import com.example.weatherapp.data.api.GeoCodeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL = "http://api.weatherunlocked.com/api/"
private const val GEO_BASE_URL = "http://api.openweathermap.org/geo/1.0/"

private const val APP_KEY = "0c156d8e"
private const val QUERY_APP_KEY = "app_id"

private const val GEO_APP_KEY = "27c7562ae39fc5e14083e53be2619ffe"
private const val GEO_QUERY_APP_KEY = "appid"

private const val API_KEY = "0a29bc44d7c193da4a1f63baf872b442"
private const val QUERY_API_KEY = "app_key"

private const val LANG_CODE = "ru"
private const val QUERY_LANG = "lang"

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @Singleton
    @ApiKeyInterceptor
    fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .addQueryParameter(QUERY_APP_KEY, APP_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Singleton
    @GeoApiKeyInterceptor
    fun provideGeoApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(GEO_QUERY_APP_KEY, GEO_APP_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Singleton
    @LangInterceptor
    fun provideLangInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_LANG, LANG_CODE)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Singleton
    @HttpClient
    fun provideHttpClient(
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
        @LangInterceptor langInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(langInterceptor)
            .also {
                it.addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(
                            HttpLoggingInterceptor.Level.BODY
                        )
                )
            }
            .build()

    @Provides
    @Singleton
    @GeoHttpClient
    fun provideGeoHttpClient(
        @GeoApiKeyInterceptor GeoApiKeyInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(GeoApiKeyInterceptor)
            .also {
                it.addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(
                            HttpLoggingInterceptor.Level.BODY
                        )
                )
            }
            .build()

    @Provides
    @Singleton
    fun provideApi(
        @HttpClient okhttp: OkHttpClient
    ): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

    @Provides
    @Singleton
    fun provideGeoApi(
        @GeoHttpClient okhttp: OkHttpClient
    ): GeoCodeApi =
        Retrofit.Builder()
            .baseUrl(GEO_BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeoCodeApi::class.java)
}

//annotations
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LangInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GeoHttpClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKeyInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GeoApiKeyInterceptor
