package com.weatherapp.commons

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}

internal class DefaultDispatcherProvider : DispatcherProvider {
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}
