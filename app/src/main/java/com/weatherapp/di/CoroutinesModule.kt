package com.weatherapp.di

import com.weatherapp.commons.DefaultDispatcherProvider
import com.weatherapp.commons.DispatcherProvider
import org.koin.dsl.module

val coroutinesModule = module {

    single{ providesDispatcherProvider() }
}

internal fun providesDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()