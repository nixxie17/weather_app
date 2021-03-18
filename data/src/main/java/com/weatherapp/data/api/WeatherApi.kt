package com.weatherapp.data.api

import com.weatherapp.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double): WeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByCityName(@Query("q") cityName: String): WeatherResponse
}