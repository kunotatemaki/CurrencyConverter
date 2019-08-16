package com.raul.androidapps.softwaretestrevolut.repository

import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.network.Resource
import io.reactivex.Observable
import io.reactivex.Single


interface Repository {

    suspend fun getRatesWithCoroutines(base: String): Resource<Rates?>

    fun getRatesWithRxJava(base: String): Single<Rates?>

}