package com.raul.androidapps.softwaretestrevolut.network

import com.raul.androidapps.softwaretestrevolut.domain.responses.RatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutApi {

    @GET("latest")
    suspend fun getLatestRatesWithCoroutines(@Query("base") base: String): Response<RatesResponse>

    //todo same with RxJava

}
