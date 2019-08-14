package com.raul.androidapps.softwaretestrevolut.network

interface NetworkServiceFactory {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org"

    }

    fun getServiceInstance(): RevolutApi
}

