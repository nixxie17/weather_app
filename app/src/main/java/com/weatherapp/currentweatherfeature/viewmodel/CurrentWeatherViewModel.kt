package com.weatherapp.currentweatherfeature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.commons.DispatcherProvider
import com.weatherapp.currentweatherfeature.state.CurrentWeatherViewState
import com.weatherapp.domain.model.Location
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.usecase.GetWeatherByCityNameUseCase
import com.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentWeatherViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
) : ViewModel() {

    private var currentWeatherJob: Job? = null

    private var _weatherViewState = MutableLiveData<CurrentWeatherViewState>()
    val weatherViewState: LiveData<CurrentWeatherViewState>
        get() = _weatherViewState

    fun initView() {
        _weatherViewState.value = CurrentWeatherViewState(
            isLoading = true,
            error = null,
            city = null,
            main = null,
            description = null,
            icon = null
        )
    }

    override fun onCleared() {
        super.onCleared()
        currentWeatherJob?.cancel()
    }

    fun loadWeatherByLocation(location: Location) {
        currentWeatherJob = viewModelScope.launch(dispatcherProvider.io) {
            try {
                val weather = getWeatherByLocationUseCase(location)
                updateViewState(weather)
            } catch (e: Throwable) {
                notifyErrorOnMain(e)
            }
        }
    }

    fun loadWeatherByCityName(cityName: String) {
        currentWeatherJob = viewModelScope.launch(dispatcherProvider.io) {
            try {
                val weather = getWeatherByCityNameUseCase(cityName)
                updateViewState(weather)
            }catch (e: Throwable){
                notifyErrorOnMain(e)
            }

        }
    }

    private suspend fun updateViewState(weather: Weather) =
        withContext(dispatcherProvider.main) {
            if (_weatherViewState.value != null) {
                _weatherViewState.value = _weatherViewState.value?.copy(
                    isLoading = false,
                    error = null,
                    city = weather.city,
                    main = weather.main,
                    description = weather.description,
                    icon = weather.icon,
                    temp = weather.temp,
                    tempMin = weather.tempMin,
                    tempMax = weather.tempMax,
                    humidity = weather.humidity,
                    pressure = weather.pressure
                )
            }
        }

    private suspend fun notifyErrorOnMain(e: Throwable) = withContext(dispatcherProvider.main){
        val msg = e.localizedMessage
        _weatherViewState.value = _weatherViewState.value?.copy(
            error = com.weatherapp.commons.Error(
                //strings should be provided by a string resources file through an injected resource helper
                // separating the context from the view model
                msg ?: "Unknown error"
            )
        )
    }
}