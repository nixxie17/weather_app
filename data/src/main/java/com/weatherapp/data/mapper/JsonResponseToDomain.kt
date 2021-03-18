package com.weatherapp.data.mapper

import com.weatherapp.data.response.WeatherResponse
import com.weatherapp.domain.model.Weather

//this is static just for this showcase
const val BASE_IMAGES_URL = "https://openweathermap.org/img/wn/"
const val IMAGE_FORMAT_SUFFIX = "@2x.png"

internal fun WeatherResponse.toDomain(): Weather = Weather(
    id = this.id,
    city = this.name,
    main = this.weather[0].main,
    description = this.weather[0].description,
    icon = "$BASE_IMAGES_URL${this.weather[0].icon}$IMAGE_FORMAT_SUFFIX",
    temp = this.main.temp,
    pressure = this.main.pressure,
    humidity = this.main.humidity,
    tempMax = this.main.temp_max,
    tempMin = this.main.temp_min
)
