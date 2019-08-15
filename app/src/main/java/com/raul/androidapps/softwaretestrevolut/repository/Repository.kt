package com.raul.androidapps.softwaretestrevolut.repository

import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.network.Resource


interface Repository {

    suspend fun getRates(base: String): Resource<Rates?>

}