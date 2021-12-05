package com.rami.weatherapp.ui.home

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import com.rami.weatherapp.R
import com.rami.weatherapp.data.model.WeatherResponse
import com.rami.weatherapp.data.model.utils.BaseApiResponse
import com.rami.weatherapp.databinding.HomeFragmentBinding
import com.rami.weatherapp.ui.base.BaseFragment
import com.rami.weatherapp.utils.ApiStatus
import com.rami.weatherapp.utils.LocationUtil
import com.rami.weatherapp.utils.ScopeEnum
import com.rami.weatherapp.utils.extentions.convertKelvinToCelsius
import com.rami.weatherapp.utils.extentions.convertKelvinToFahrenheit
import com.rami.weatherapp.utils.extentions.hideKeyboard
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    private val scope: Scope =
        getKoin().getOrCreateScope(ScopeEnum.Home.scope, named(ScopeEnum.Home.scope))
    override val viewModel: HomeViewModel = scope.get()
    override fun getLayoutId(): Int = R.layout.home_fragment
    var isFirstLaunch: Boolean = true

    companion object {
        var PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                getCurrentLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.weatherDataResponse.observe(this, {
            it?.let {
                handleWeatherDataByNameResponse(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        // to prevent recall get weather data after App returned foreground from background
        if (isFirstLaunch) {
            isFirstLaunch = false
            getCurrentWeatherData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getCurrentWeatherData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun getCurrentWeatherData() {
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (hasPermissions(activity as Context, PERMISSIONS)) {
                    getCurrentLocation()
                } else {
                    permReqLauncher.launch(PERMISSIONS)
                }
            } else {
                getCurrentLocation()
            }
        }
    }

    fun getCurrentLocation() {
        activity?.let {
            LocationUtil.getCurrentLocationOneTime(it) { location ->
                viewModel.getCurrentWeatherData(location)
            }
        }
    }

    private fun handleWeatherDataByNameResponse(response: BaseApiResponse<WeatherResponse>) {
        if (response.apiStatus == ApiStatus.SUCCESS) {
            hideLoading()
            updateUi(response.data)
        } else {
            showErrorMsg(response.error)
        }
    }

    private fun updateUi(data: WeatherResponse?) {
        data?.let {
            binding.searchView.setQuery(it.name, false)
            binding.tvKelvin.text = (it.main?.temp ?: 0.0).toString()
            binding.tvFahrenheit.text =
                (it.main?.temp ?: 0.0).convertKelvinToFahrenheit().toString()
            binding.tvCelsius.text = (it.main?.temp ?: 0.0).convertKelvinToCelsius().toString()
            activity?.hideKeyboard(binding.searchView)
        }

    }
}