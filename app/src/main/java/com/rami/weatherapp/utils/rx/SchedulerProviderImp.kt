package com.rami.weatherapp.utils.rx

import io.reactivex.*

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
class SchedulerProviderImp : SchedulerProvider {
    override fun <T> ioToMainObservableScheduler(): ObservableTransformer<T, T> =
        ObservableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun <T> ioToMainSingleScheduler(): SingleTransformer<T, T> =
        SingleTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun ioToMainCompletableScheduler(): CompletableTransformer =
        CompletableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun <T> ioToMainFlowableScheduler(): FlowableTransformer<T, T> =
        FlowableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun <T> ioToMainMaybeScheduler(): MaybeTransformer<T, T> =
        MaybeTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }
}