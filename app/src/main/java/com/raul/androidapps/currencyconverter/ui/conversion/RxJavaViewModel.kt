package com.raul.androidapps.currencyconverter.ui.conversion

import androidx.annotation.VisibleForTesting
import com.raul.androidapps.currencyconverter.domain.model.Rates
import com.raul.androidapps.currencyconverter.repository.Repository
import com.raul.androidapps.currencyconverter.ui.common.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RxJavaViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {

    private val disposableComposer: CompositeDisposable = CompositeDisposable()
    private lateinit var disposableFetch: DisposableObserver<Rates?>

    override fun stopFetchingRates() {
        disposableFetch.dispose()
        disposableComposer.remove(disposableFetch)
    }

    override fun onCleared() {
        super.onCleared()
        disposableComposer.clear()
    }

    override fun startFetchingRatesAsync() {
        disposableFetch = object : DisposableObserver<Rates?>() {
            override fun onComplete() {}
            override fun onNext(rates: Rates) {
                updateObservableAsync(rates)
            }
            override fun onError(e: Throwable) {}
        }
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
            .flatMapSingle { repository.getRatesWithRxJava(baseCurrency) }
            .doOnError {
                Timber.d("Error Fetching conversion rates")
            }
            .retry()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                disposableFetch
            )
        disposableComposer.add(disposableFetch)

    }


    @VisibleForTesting
    fun updateObservableAsync(rates: Rates?) {

        disposableComposer.add(Single.just(getNewRatesSorted(rates)).subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribeWith(
                object : DisposableSingleObserver<Rates?>() {
                    override fun onSuccess(sortedRates: Rates) {
                        ratesObservable.postValue(sortedRates)
                    }

                    override fun onError(e: Throwable) {}
                }
            )
        )

    }


}
