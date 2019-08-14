package com.raul.androidapps.softwaretestrevolut.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import com.raul.androidapps.softwaretestrevolut.utils.RevolutConstants

abstract class BaseViewModel : ViewModel() {

    protected val ratesObservable: MutableLiveData<Rates> = MutableLiveData()
    protected var baseCurrency: String = RevolutConstants.DEFAULT_CURRENCY

    fun getRates(): LiveData<Rates> = ratesObservable

    abstract fun startFetchingRates()
    abstract fun stopFetchingRates()

    fun changeCurrency(base: String){
        stopFetchingRates()
        baseCurrency = base
        startFetchingRates()
    }


}
