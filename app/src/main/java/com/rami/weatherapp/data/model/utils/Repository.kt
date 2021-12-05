package com.rami.weatherapp.data.model.utils

import android.location.Location
import com.rami.weatherapp.BuildConfig
import com.rami.weatherapp.data.model.WeatherResponse
import io.reactivex.Single

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
class Repository(private val dataSource: DataSource) {

    fun getCurrentWeatherData(cityName: String): Single<WeatherResponse> {
        return dataSource.apiService.getCurrentWeatherData(cityName, BuildConfig.API_KEY)
    }

    fun getCurrentWeatherData(location: Location): Single<WeatherResponse> {
        return dataSource.apiService.getCurrentWeatherData(
            location.latitude,
            location.longitude,
            BuildConfig.API_KEY
        )
    }
}