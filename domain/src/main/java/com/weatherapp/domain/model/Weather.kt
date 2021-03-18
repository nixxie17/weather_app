package com.weatherapp.domain.model

data class Weather(
    val id: Int,
    val city: String,
    val main: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val tempMin: Double,
    val tempMax: Double
)