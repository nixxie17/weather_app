package com.weatherapp.data.repository

import com.weatherapp.data.api.WeatherApi
import com.weatherapp.data.mapper.toDomain
import com.weatherapp.domain.model.Location
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherByLocation(location: Location): Weather =
        weatherApi.getCurrentWeatherByLocation(location.lat, location.lon).toDomain()


    override suspend fun getWeatherByCityName(cityName: String): Weather =
        weatherApi.getCurrentWeatherByCityName(cityName).toDomain()
}
