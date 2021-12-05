package com.rami.weatherapp.di

import com.rami.weatherapp.data.remore.AuthInterceptor
import com.rami.weatherapp.data.remore.RetroClient
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
val networkModule: Module = module {
    single { AuthInterceptor() }
    single { RetroClient.createApiService(get()) }
}