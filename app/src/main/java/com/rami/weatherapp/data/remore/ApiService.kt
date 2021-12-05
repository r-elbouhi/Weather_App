package com.rami.weatherapp.data.remore

import com.rami.weatherapp.data.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
interface ApiService {

    @GET("data/2.5/weather")
    fun getCurrentWeatherData(
        @Query(value = "q") cityName: String,
        @Query("appid") appId: String
    ): Single<WeatherResponse>

    @GET("data/2.5/weather")
    fun getCurrentWeatherData(
        @Query(value = "lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String
    ): Single<WeatherResponse>
}