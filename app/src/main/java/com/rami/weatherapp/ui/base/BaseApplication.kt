package com.rami.weatherapp.ui.base

import android.app.Application
import com.rami.weatherapp.di.networkModule
import com.rami.weatherapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }

    companion object {
        lateinit var instance: BaseApplication
            private set
    }
}