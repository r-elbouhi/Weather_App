package com.rami.weatherapp.data.model

import com.squareup.moshi.Json

/**
 * Created by Rami El-bouhi on 01,December,2021
 */
data class WeatherResponse(
    @Json(name = "coord")
    val coordinate: Coordinate? = null,
    val weather: List<Weather>? = null,
    val base: String? = null,
    val main: Main? = null,
    val visibility: Int? = null,
    val wind: Wind? = null,
    val clouds: Clouds? = null,
    val dt: Int? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val id: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
    val message: String? = null,
)

data class Coordinate(
    val lon: Double? = null,
    val lat: Double? = null
)

data class Weather(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

data class Main(
    val temp: Double? = null,
    @Json(name = "feels_like")
    val feelsLike: Double? = null,
    @Json(name = "temp_min")
    val tempMin: Double? = null,
    @Json(name = "temp_max")
    val tempMax: Double? = null,
    val pressure: Int? = null,
    val humidity: Int? = null
)

data class Wind(
    val speed: Double? = null,
    val deg: Int? = null
)

data class Clouds(
    val all: Int? = null
)

data class Sys(
    val type: Int? = null,
    val id: Int? = null,
    val country: String? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null
)