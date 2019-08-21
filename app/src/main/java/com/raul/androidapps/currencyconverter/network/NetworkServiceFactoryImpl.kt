package com.raul.androidapps.currencyconverter.network

import com.raul.androidapps.currencyconverter.network.NetworkServiceFactory.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkServiceFactoryImpl @Inject constructor(): NetworkServiceFactory {

    @Volatile
    private var instance: NetworkApi? = null

    override fun getServiceInstance(): NetworkApi =
        instance ?: buildNetworkService().also { instance = it }

    private fun buildNetworkService(): NetworkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(NetworkApi::class.java)


}