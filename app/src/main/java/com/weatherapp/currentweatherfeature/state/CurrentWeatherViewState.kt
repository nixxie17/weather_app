package com.weatherapp.currentweatherfeature.state

import com.weatherapp.commons.Error

data class CurrentWeatherViewState(
    val isLoading: Boolean,
    val error: Error?,
    val city: String?,
    val main: String?,
    val description: String?,
    val icon: String?,
    val temp: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0
)