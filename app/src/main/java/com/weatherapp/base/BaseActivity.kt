package com.weatherapp.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.weatherapp.commons.NetworkUtils

open class BaseActivity: AppCompatActivity() {

    protected fun onNetworkChange(block: (Boolean) -> Unit) {
        NetworkUtils.getNetworkStatus(this)
            .observe(this, Observer { isConnected ->
                block(isConnected)
            })
    }
}