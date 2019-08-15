package com.raul.androidapps.softwaretestrevolut.ui.conversion

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.network.Resource
import com.raul.androidapps.softwaretestrevolut.repository.Repository
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoroutineViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {


    private lateinit var job: Job

    override fun startFetchingRates() {
        job = startFetchingRatesAsync(baseCurrency)
    }

    override fun stopFetchingRates() {
        job.cancel()
    }

    @VisibleForTesting
    fun startFetchingRatesAsync(base: String): Job =
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val ratesResponse = repository.getRates(base)
                if (ratesResponse.status == Resource.Status.SUCCESS) {
                    updateObservableAsync(ratesResponse.data)
                }
                delay(1000)
            }
        }

    private fun updateObservableAsync(rates: Rates?): Job =
        viewModelScope.launch(Dispatchers.Default) {
            val sortedRates = getNewRatesSorted(rates)
            ratesObservable.postValue(sortedRates)
        }


}
