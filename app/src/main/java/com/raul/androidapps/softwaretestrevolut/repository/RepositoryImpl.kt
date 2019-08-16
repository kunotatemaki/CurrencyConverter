package com.raul.androidapps.softwaretestrevolut.repository

import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactory
import com.raul.androidapps.softwaretestrevolut.network.Resource
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(private val networkServiceFactory: NetworkServiceFactory): Repository {

    override suspend fun getRatesWithCoroutines(base: String): Resource<Rates?> {
        val resp = networkServiceFactory.getServiceInstance().getLatestRatesWithCoroutines(base)
        return if(resp.isSuccessful && resp.body() != null){
            Resource.success(resp.body())
        }else{
            Resource.error(resp.message(), null)
        }
    }

    override fun getRatesWithRxJava(base: String): Single<Rates?> =
        networkServiceFactory.getServiceInstance().getLatestRatesWithRxJava(base)



}