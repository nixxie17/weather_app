package com.weatherapp.di

import com.weatherapp.data.repository.WeatherRepositoryImpl
import com.weatherapp.domain.repository.WeatherRepository
import org.koin.dsl.module


val remoteDataSourceModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}