package com.weatherapp

import android.app.Application
import com.weatherapp.di.coroutinesModule
import com.weatherapp.di.networkModule
import com.weatherapp.di.remoteDataSourceModule
import com.weatherapp.di.useCaseModule
import com.weatherapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApp)
            modules(
                networkModule,
                remoteDataSourceModule,
                useCaseModule,
                coroutinesModule,
                viewModelsModule
            )
        }
    }

}