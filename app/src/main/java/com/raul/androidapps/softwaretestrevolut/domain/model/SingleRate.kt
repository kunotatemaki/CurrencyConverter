package com.raul.androidapps.softwaretestrevolut.domain.model

import java.math.BigDecimal


data class SingleRate constructor(
    val code: String,
    val rate: BigDecimal
) {

    fun calculatePrice(basePrice: BigDecimal): String {
        //todo convert to proper format
        return (rate * basePrice).toString()
    }

}