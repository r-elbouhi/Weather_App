package com.rami.weatherapp.ui.base

import androidx.lifecycle.ViewModel
import com.rami.weatherapp.data.model.utils.BaseApiResponse
import com.rami.weatherapp.data.model.utils.Repository
import com.rami.weatherapp.utils.SingleLiveEvent
import com.rami.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
open class BaseViewModel(
    val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private val internalState = SingleLiveEvent<BaseApiResponse<Any>>()
    val state: SingleLiveEvent<BaseApiResponse<Any>> = internalState

    fun <T> subscribe(
        observable: Single<T>?,
        success: Consumer<T>?,
        error: Consumer<Throwable>? = Consumer { }
    ) {
        observable
            ?.compose(schedulerProvider.ioToMainSingleScheduler())
            ?.compose { single ->
                composeSingle<T>(single)
            }
            ?.subscribe(success, error)?.let { compositeDisposable.add(it) }
    }

    private fun <T> composeSingle(single: Single<T>): Single<T> {
        return single
            .doOnError {
                internalState.postValue(BaseApiResponse.handleError(it))
            }
            .doOnSubscribe {
                internalState.postValue(BaseApiResponse.loading())
            }
    }
}