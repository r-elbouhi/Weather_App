package com.rami.weatherapp.utils

import android.content.Context
import android.location.LocationManager

/**
 * Created by Rami El-bouhi on 04,December,2021
 */

fun isGpsEnabled(context: Context?) =
    (context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        .isProviderEnabled(LocationManager.GPS_PROVIDER)