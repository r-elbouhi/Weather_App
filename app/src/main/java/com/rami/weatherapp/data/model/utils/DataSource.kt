package com.rami.weatherapp.data.model.utils

import com.rami.weatherapp.data.remore.ApiService

/**
 * Created by Rami El-bouhi on 30,November,2021
 *
 * will hold all possible data source inside App e.g. APIs, pref and database
 */
class DataSource(
    var apiService: ApiService
)