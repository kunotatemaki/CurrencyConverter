package com.raul.androidapps.currencyconverter.network

import com.raul.androidapps.currencyconverter.domain.model.Rates
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("latest")
    suspend fun getLatestRatesWithCoroutines(@Query("base") base: String): Response<Rates?>

    @GET("latest")
    fun getLatestRatesWithRxJava(@Query("base") base: String): Single<Rates?>

}
