package com.raul.androidapps.softwaretestrevolut.network

interface NetworkServiceFactory {

    companion object {
        const val BASE_URL = "https://api.github.com"

    }

    fun getServiceInstance(): RevolutApi
}

