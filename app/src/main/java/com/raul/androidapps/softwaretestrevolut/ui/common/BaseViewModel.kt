package com.raul.androidapps.softwaretestrevolut.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import com.raul.androidapps.softwaretestrevolut.utils.RevolutConstants
import java.util.*

abstract class BaseViewModel : ViewModel() {

    protected val ratesObservable: MutableLiveData<Rates> = MutableLiveData()

    @get:Synchronized
    protected var baseCurrency: String = RevolutConstants.DEFAULT_CURRENCY

    var basePrice: String = "0"

    open fun getRates(): LiveData<Rates> = ratesObservable

    fun setRates(list: List<SingleRate>){
        ratesObservable.value = Rates(list)
    }

    abstract fun startFetchingRatesAsync()
    abstract fun stopFetchingRates()

    open fun changeCurrency(base: String) {
        baseCurrency = base
    }

    protected fun getNewRatesSorted(rates: Rates?): Rates? {
        rates?.getListWithCalculatedPrices(basePrice, Locale.getDefault())
        val oldRates = ratesObservable.value
        if (oldRates == null) {
            return rates
        } else {
            rates?.list?.firstOrNull { it.isBasePrice }?.let { newBase ->
                val listOfCodesInPreviousOrder = oldRates.list.map { it.code }.toMutableList()
                val positionOfNewBaseInOldList = listOfCodesInPreviousOrder.indexOf(newBase.code)
                if (positionOfNewBaseInOldList >= 0) {
                    listOfCodesInPreviousOrder.apply {
                        removeAt(positionOfNewBaseInOldList)
                        add(0, newBase.code)
                    }
                    val orderByCode =
                        listOfCodesInPreviousOrder.withIndex().associate { it.value to it.index }
                    val newRatesWithSortedWithPreviousList: List<SingleRate> =
                        rates.list.sortedBy { orderByCode[it.code] }
                    return Rates(newRatesWithSortedWithPreviousList)
                }
            }
        }
        return rates
    }


}
