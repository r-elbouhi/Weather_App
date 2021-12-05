package com.rami.weatherapp.utils

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
const val UN_AUTHORIZED_CODE = 401
const val GPS_REQUEST_CODE = 100

enum class ApiStatus {
    LOADING,
    SUCCESS,
    EMPTY_DATA,
    NO_INTERNET,
    ERROR,
    UN_AUTHORIZED
}

enum class ScopeEnum(val scope: String) {
    Home("home")
}