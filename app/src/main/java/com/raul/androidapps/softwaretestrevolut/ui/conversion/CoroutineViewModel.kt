package com.raul.androidapps.softwaretestrevolut.ui.conversion

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
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

            rates?.getListWithCalculatedPrices(basePrice)
            val oldRates = ratesObservable.value
            if(oldRates == null){
                ratesObservable.postValue(rates)
            }else {

                rates?.list?.firstOrNull { it.isBasePrice }?.let { newBase ->
                    Timber.d("rukia new code = ${newBase.code}")
                    if(newBase.code == "AUD"){
                        Timber.d("")
                    }
                    val listOfCodesInPreviousOrder = oldRates.list.map { it.code }.toMutableList()
                    val positionOfNewBaseInOldList = listOfCodesInPreviousOrder.indexOf(newBase.code)
                    if(positionOfNewBaseInOldList >= 0) {
                        listOfCodesInPreviousOrder.apply{
                            removeAt(positionOfNewBaseInOldList)
                            add(0, newBase.code)
                        }
                        val orderByCode = listOfCodesInPreviousOrder.withIndex().associate { it.value to it.index }
                        val newRatesWithSortedWithPreviousList: List<SingleRate> = rates.list.sortedBy { orderByCode[it.code] }
                        ratesObservable.postValue(Rates(newRatesWithSortedWithPreviousList))
                    }
                }
            }
        }

}
