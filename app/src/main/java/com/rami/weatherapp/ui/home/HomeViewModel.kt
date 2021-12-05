package com.rami.weatherapp.ui.home

import android.location.Location
import com.rami.weatherapp.R
import com.rami.weatherapp.data.model.WeatherResponse
import com.rami.weatherapp.data.model.utils.BaseApiResponse
import com.rami.weatherapp.data.model.utils.CustomError
import com.rami.weatherapp.data.model.utils.Repository
import com.rami.weatherapp.ui.base.BaseViewModel
import com.rami.weatherapp.utils.SingleLiveEvent
import com.rami.weatherapp.utils.rx.SchedulerProvider

class HomeViewModel(repository: Repository, schedulerProvider: SchedulerProvider) :
    BaseViewModel(repository, schedulerProvider) {

    val weatherDataResponse: SingleLiveEvent<BaseApiResponse<WeatherResponse>> =
        SingleLiveEvent()

    fun getCurrentWeatherData(cityName: String?) {
        if (cityName.isNullOrBlank()) {
            weatherDataResponse.value =
                BaseApiResponse.error(CustomError(message = R.string.please_enter_city_name))
        } else {
            subscribe(repository.getCurrentWeatherData(cityName), {
                weatherDataResponse.value = BaseApiResponse.success(data = it)
            })
        }
    }

    fun getCurrentWeatherData(location: Location?) {
        if (location == null) {
            weatherDataResponse.value =
                BaseApiResponse.error(CustomError(message = R.string.cannot_get_your_location))
        } else {
            subscribe(repository.getCurrentWeatherData(location), {
                weatherDataResponse.value = BaseApiResponse.success(data = it)
            })
        }
    }
}