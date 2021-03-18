package com.weatherapp.di

import com.weatherapp.domain.repository.WeatherRepository
import com.weatherapp.domain.usecase.GetWeatherByCityNameUseCase
import com.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { provideGetWeatherByLocationUseCase(get()) }
    single { provideGetWeatherByCityNameUseCase(get()) }
}

fun provideGetWeatherByLocationUseCase(
    weatherRepository: WeatherRepository
) = GetWeatherByLocationUseCase(weatherRepository)

fun provideGetWeatherByCityNameUseCase(
    weatherRepository: WeatherRepository
) = GetWeatherByCityNameUseCase(weatherRepository)