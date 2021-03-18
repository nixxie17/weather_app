package com.weatherapp.currentweatherfeature

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import com.weatherapp.base.BaseActivity
import com.weatherapp.commons.Error
import com.weatherapp.commons.hide
import com.weatherapp.commons.show
import com.weatherapp.commons.showSnackbar
import com.weatherapp.currentweatherfeature.state.CurrentWeatherViewState
import com.weatherapp.currentweatherfeature.viewmodel.CurrentWeatherViewModel
import com.weatherapp.weatherapp.R
import com.weatherapp.weatherapp.databinding.CurrentWeatherActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

private const val LOCATION_PERMISSION_CODE = 1001

class CurrentWeatherActivity : BaseActivity() {
    private val currentWeatherViewModel by viewModel<CurrentWeatherViewModel>()
    private lateinit var binding: CurrentWeatherActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.current_weather_activity)
        setListeners()

        currentWeatherViewModel.initView()
        observeViewState()
        getDeviceLocation()
    }

    private fun setListeners(){
        binding.btnForecast.setOnClickListener{
            binding.searchEditText.text.toString().trim().let {
                if(it.isNotEmpty()){
                    binding.searchResultsProgressBar.show()
                    currentWeatherViewModel.loadWeatherByCityName(it)
                }else{
                    showSnackbar(binding.resultsHolder, getString(R.string.no_city_name), true)
                }
            }
        }
    }

    private fun getDeviceLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), LOCATION_PERMISSION_CODE
            )
            return
        }
        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    currentWeatherViewModel.loadWeatherByLocation(
                        com.weatherapp.domain.model.Location(
                            it.latitude,
                            it.longitude
                        )
                    )
                }
            }
    }

    private fun observeViewState() {
        currentWeatherViewModel.weatherViewState.observe(this, {
            it.error?.let { e ->
                //simple display of the error message just for this showcase - error codes should be passed and handled
                // with a proper error message
                onError(e)
            }
            setLoading(it.isLoading)
            bindWeatherData(it)
        })
    }

    private fun onError(e: Error) {
        showSnackbar(binding.resultsHolder, e.message, true)
        binding.searchResultsProgressBar.hide()
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.searchResultsProgressBar.show()
        } else {
            binding.searchResultsProgressBar.hide()
        }
    }

    private fun bindWeatherData(state: CurrentWeatherViewState) {
        Picasso.get()
            .load(state.icon)
            .into(binding.forecastIcon)

        binding.forecastIcon.show()
        binding.searchEditText.setText(state.city)
        binding.tvMain.text = state.main
        binding.tvCityName.text = state.city?.toUpperCase(Locale.getDefault())
        "${getString(R.string.temperature)} ${state.temp.toInt()}${getString(R.string.symbol_degrees)}".let {
            binding.tvTemp.text = it
        }
        "${getString(R.string.max_temp)} ${state.tempMax.toInt()}${getString(R.string.symbol_degrees)}".let {
            binding.tvMaxTemp.text = it
        }
        "${getString(R.string.min_temp)} ${state.tempMin.toInt()}${getString(R.string.symbol_degrees)}".let {
            binding.tvMinTemp.text = it
        }
        "${getString(R.string.humidity)} ${state.humidity}${getString(R.string.symbol_humidity)}".let {
            binding.tvHumidity.text = it
        }
        "${getString(R.string.pressure)} ${state.pressure}${getString(R.string.symbol_pressure)}".let {
            binding.tvPressure.text = it
        }
        "\" ${state.description} \"".let {
            binding.tvDescription.text = it
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                if (requestCode == LOCATION_PERMISSION_CODE && grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    getDeviceLocation()
                } else {
                    //the whole "permissions not granted" usecase should be handled with some backing solutions,
                        //for the purpose of this showcase only snackbar is displayed
                    showSnackbar(
                        binding.resultsHolder,
                        getString(R.string.device_location_error),
                        true
                    )
                }
            }
        }
    }
}