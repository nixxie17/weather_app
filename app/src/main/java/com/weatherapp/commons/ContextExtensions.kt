package com.weatherapp.commons

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

internal fun Context.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

internal fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}