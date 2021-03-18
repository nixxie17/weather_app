package com.weatherapp.domain.usecase

import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository

class GetWeatherByCityNameUseCase(
    private val repository: WeatherRepository
): BaseUseCase<String, Weather> {
    override suspend fun invoke(params: String): Weather = repository.getWeatherByCityName(params)
}