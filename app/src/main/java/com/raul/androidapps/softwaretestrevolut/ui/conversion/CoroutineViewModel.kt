package com.raul.androidapps.softwaretestrevolut.ui.conversion

import androidx.annotation.VisibleForTesting
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.network.Resource
import com.raul.androidapps.softwaretestrevolut.repository.Repository
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class CoroutineViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {


    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var fetchingJob: Job

    override fun startFetchingRates() {
        startFetchingRatesAsync(baseCurrency)
    }

    override fun stopFetchingRates() {
        fetchingJob.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    @VisibleForTesting
    fun startFetchingRatesAsync(base: String): Job {
        fetchingJob = Job(viewModelJob)
        return viewModelScope.launch(Dispatchers.IO + fetchingJob) {
            while (true) {
                Timber.d("rukia fetching")
                val ratesResponse = repository.getRates(base)
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
