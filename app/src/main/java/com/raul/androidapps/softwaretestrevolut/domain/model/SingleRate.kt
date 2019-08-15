package com.raul.androidapps.softwaretestrevolut.domain.model

import timber.log.Timber
import java.math.BigDecimal


data class SingleRate constructor(
    val code: String,
    val rate: BigDecimal
) {

    fun calculatePrice(basePrice: BigDecimal): String {
        //todo convert to proper format
        if(code == "EUR"){
            Timber.d("eur rate: $rate, basePrice: $basePrice")
        }
        return (rate * basePrice).toString()
    }

}