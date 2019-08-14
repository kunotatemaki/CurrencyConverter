package com.raul.androidapps.softwaretestrevolut.ui.main

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwaretestrevolut.RevolutApplication
import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactory
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(networkServiceFactory: NetworkServiceFactory) : ViewModel() {
    // TODO: Implement the ViewModel

    init {
        viewModelScope.launch {
            val response = networkServiceFactory.getServiceInstance().getLatestRatesWithCoroutines("EUR")
            Timber.d("")
        }
    }



}
