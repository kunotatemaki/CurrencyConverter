package com.raul.androidapps.currencyconverter.ui.conversion

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.raul.androidapps.currencyconverter.domain.model.BooleanKey
import com.raul.androidapps.currencyconverter.domain.model.Rates
import com.raul.androidapps.currencyconverter.domain.model.SingleRate
import com.raul.androidapps.currencyconverter.network.Resource
import com.raul.androidapps.currencyconverter.repository.Repository
import com.raul.androidapps.currencyconverter.ui.common.BaseViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

open class CoroutineViewModel @Inject constructor(private val repository: Repository, private val key: BooleanKey) :
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
        val name = key.name()
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

    @VisibleForTesting
    override fun getRates(): LiveData<Rates> {
        return super.getRates()
    }

    @VisibleForTesting
    override fun setRates(list: List<SingleRate>) {
        super.setRates(list)
    }

    @VisibleForTesting
    override fun changeCurrency(base: String) {
        super.changeCurrency(base)
    }
}
