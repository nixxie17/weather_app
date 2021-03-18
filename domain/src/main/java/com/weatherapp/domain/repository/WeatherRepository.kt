package com.weatherapp.domain.repository

import com.weatherapp.domain.model.Location
import com.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByLocation(location: Location): Weather

    suspend fun getWeatherByCityName(cityName: String): Weather
}