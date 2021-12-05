package com.rami.weatherapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.rami.weatherapp.ui.base.BaseApplication

/**
 * Created by Rami El-bouhi on 04,December,2021
 */
object LocationUtil {

    private var locationCallback: LocationCallback? = null
    private val fusedLocationClient by lazy {
        getFusedLocationProviderClient(BaseApplication.instance)
    }
    private val settingsClient: SettingsClient by lazy {
        LocationServices.getSettingsClient(BaseApplication.instance)
    }
    private val locationRequest = LocationRequest.create().apply {
        interval = 100
        fastestInterval = 50
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 100
    }

    /**
     * This will be used only when provider has request and doing work to draw his marker on the map.
     *
     * It is the responsibility of the caller to call [stopCurrentLocationUpdates] after finishing using
     * this function to prevent memory leak and battery consumption.
     */
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        body: (newLocation: Location?) -> Unit
    ) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) =
                body(locationResult?.lastLocation)
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun stopCurrentLocationUpdates() = locationCallback?.let {
        fusedLocationClient.removeLocationUpdates(it)
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(
        context: Activity,
        body: (location: Location?) -> Unit
    ) {
        fusedLocationClient.lastLocation.addOnCompleteListener(context) {
            it.run {
                if (isSuccessful && result != null) body(result) else body(null)
            }
        }
    }

    /**
     * This gets current accurate location and stop receiving updates.
     */
    fun getCurrentLocationOneTime(
        body: (location: Location?) -> Unit
    ) {
        getCurrentLocation {
            body.invoke(it)
            stopCurrentLocationUpdates()
        }
    }

    /**
     * This check GPS state then gets current accurate location and stop receiving updates
     */
    fun getCurrentLocationOneTime(
        activity: Activity,
        body: (location: Location?) -> Unit
    ) {
        requestLocationUpdatesWithSettingsCheck(activity) {
            body.invoke(it)
            stopCurrentLocationUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdatesWithSettingsCheck(
        activity: Activity,
        body: (newLocation: Location?) -> Unit
    ) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) =
                body(locationResult?.lastLocation)
        }
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            }
            .addOnFailureListener {
                if (it is ResolvableApiException) {
                    try {
                        it.startResolutionForResult(activity, GPS_REQUEST_CODE)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
    }
}