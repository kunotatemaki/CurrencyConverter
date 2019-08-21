package com.raul.androidapps.currencyconverter.repository

import com.raul.androidapps.currencyconverter.domain.model.Rates
import com.raul.androidapps.currencyconverter.network.NetworkServiceFactory
import com.raul.androidapps.currencyconverter.network.Resource
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(private val networkServiceFactory: NetworkServiceFactory) :
    Repository {

    override suspend fun getRatesWithCoroutines(base: String): Resource<Rates?> {
        return try {
            val resp = networkServiceFactory.getServiceInstance().getLatestRatesWithCoroutines(base)
            if (resp.isSuccessful && resp.body() != null) {
                Resource.success(resp.body())
            } else {
                Resource.error(resp.message(), null)
            }
        } catch (e: Throwable) {
            val message = "Error fetching from network"
            Timber.e(message)
            Resource.error(message, null)
        }
    }

    override fun getRatesWithRxJava(base: String): Single<Rates?> =
        networkServiceFactory.getServiceInstance().getLatestRatesWithRxJava(base)


}