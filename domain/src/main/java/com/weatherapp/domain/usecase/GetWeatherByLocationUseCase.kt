package com.weatherapp.domain.usecase

import com.weatherapp.domain.model.Location
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository

class GetWeatherByLocationUseCase(
    private val repository: WeatherRepository
): BaseUseCase<Location, Weather> {
    override suspend fun invoke(params: Location): Weather = repository.getWeatherByLocation(params)
}