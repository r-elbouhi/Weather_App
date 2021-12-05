package com.rami.weatherapp.utils.extentions

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by Rami El-bouhi on 03,December,2021
 */
fun Double.convertKelvinToFahrenheit(): Double {
    return (((this - 273.15) * 1.8) + 32).round()
}

fun Double.convertKelvinToCelsius(): Double {
    return (this - 273.15).round()
}

private fun Double.round(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}

