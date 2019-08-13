package com.raul.androidapps.softwaretestrevolut.network

import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactory.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkServiceFactoryImpl @Inject constructor(): NetworkServiceFactory {

    @Volatile
    private var instance: RevolutApi? = null

    override fun getServiceInstance(): RevolutApi =
        instance ?: buildNetworkService().also { instance = it }

    private fun buildNetworkService(): RevolutApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RevolutApi::class.java)


}