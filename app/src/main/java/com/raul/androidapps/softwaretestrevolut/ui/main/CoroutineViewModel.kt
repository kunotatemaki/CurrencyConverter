package com.raul.androidapps.softwaretestrevolut.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwaretestrevolut.network.Resource
import com.raul.androidapps.softwaretestrevolut.repository.Repository
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CoroutineViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {

    private lateinit var job: Job

    override fun startFetchingRates() {
        Timber.d("rukia arranco el trabajo $baseCurrency")
        job = startFetchingRatesAsync(baseCurrency)
    }

    override fun stopFetchingRates() {
        Timber.d("rukia paro el trabajo")
        job.cancel()
    }

    @VisibleForTesting
    fun startFetchingRatesAsync(base: String): Job =
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val rates = repository.getRates(base)
                if (rates.status == Resource.Status.SUCCESS) {
                    Timber.d("rukia meto en observable")
                    ratesObservable.postValue(rates.data)
                }else{
                    Timber.d("rukia me da error")
                }
                delay(1000)
            }
        }

}
