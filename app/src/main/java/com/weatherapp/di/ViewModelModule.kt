package com.weatherapp.di

import com.weatherapp.currentweatherfeature.viewmodel.CurrentWeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelsModule = module {
    viewModel{
            CurrentWeatherViewModel(get(),get(), get())
    }
}