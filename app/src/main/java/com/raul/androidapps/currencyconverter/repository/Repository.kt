package com.raul.androidapps.currencyconverter.repository

import com.raul.androidapps.currencyconverter.domain.model.Rates
import com.raul.androidapps.currencyconverter.network.Resource
import io.reactivex.Single


interface Repository {

    suspend fun getRatesWithCoroutines(base: String): Resource<Rates?>

    fun getRatesWithRxJava(base: String): Single<Rates?>

}