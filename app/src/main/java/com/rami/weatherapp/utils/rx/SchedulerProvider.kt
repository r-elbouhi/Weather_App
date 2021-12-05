package com.rami.weatherapp.utils.rx

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
interface SchedulerProvider {
    fun <T> ioToMainObservableScheduler(): ObservableTransformer<T, T>
    fun <T> ioToMainSingleScheduler(): SingleTransformer<T, T>
    fun ioToMainCompletableScheduler(): CompletableTransformer
    fun <T> ioToMainFlowableScheduler(): FlowableTransformer<T, T>
    fun <T> ioToMainMaybeScheduler(): MaybeTransformer<T, T>
    fun getIOThreadScheduler() = Schedulers.io()
    fun getMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()
}