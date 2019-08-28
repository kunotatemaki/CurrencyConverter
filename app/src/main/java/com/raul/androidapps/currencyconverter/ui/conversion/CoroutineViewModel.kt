package com.raul.androidapps.currencyconverter.ui.conversion

import androidx.annotation.VisibleForTesting
import com.raul.androidapps.currencyconverter.domain.model.Rates
import com.raul.androidapps.currencyconverter.network.Resource
import com.raul.androidapps.currencyconverter.repository.Repository
import com.raul.androidapps.currencyconverter.ui.common.BaseViewModel
import kotlinx.coroutines.*

open class CoroutineViewModel constructor(private val repository: Repository) :
    BaseViewModel() {


    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var fetchingJob: Job

    override fun stopFetchingRates() {
        fetchingJob.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    override fun startFetchingRatesAsync() {
        fetchingJob = Job(viewModelJob)
        viewModelScope.launch(Dispatchers.IO + fetchingJob) {
            while (true) {
                val ratesResponse = repository.getRatesWithCoroutines(baseCurrency)
                if (ratesResponse.status == Resource.Status.SUCCESS) {
                    updateObservableAsync(ratesResponse.data)
                }
                delay(1000)
            }
        }
    }

    @VisibleForTesting
    fun updateObservableAsync(rates: Rates?): Job =
        viewModelScope.launch(Dispatchers.Default) {
            val sortedRates = getNewRatesSorted(rates)
            ratesObservable.postValue(sortedRates)
        }

}
