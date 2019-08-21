package com.raul.androidapps.currencyconverter.network

interface NetworkServiceFactory {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org"

    }

    fun getServiceInstance(): NetworkApi
}

