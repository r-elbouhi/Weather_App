package com.rami.weatherapp.di

import com.rami.weatherapp.data.model.utils.DataSource
import com.rami.weatherapp.data.model.utils.Repository
import com.rami.weatherapp.ui.base.BaseViewModel
import com.rami.weatherapp.ui.home.HomeViewModel
import com.rami.weatherapp.utils.ScopeEnum
import com.rami.weatherapp.utils.rx.SchedulerProvider
import com.rami.weatherapp.utils.rx.SchedulerProviderImp
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
val viewModelModule = module {
    single { SchedulerProviderImp() as SchedulerProvider }
    factory {
        Repository(DataSource(get()))
    }
    viewModel { BaseViewModel(get(), get()) }
    scope(named(ScopeEnum.Home.scope)) {
        scoped { HomeViewModel(get(), get()) }
    }
}