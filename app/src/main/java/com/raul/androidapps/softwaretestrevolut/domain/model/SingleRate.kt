package com.raul.androidapps.softwaretestrevolut.domain.model

import java.math.BigDecimal
import java.util.*


data class SingleRate constructor(
    val code: String,
    val rate: BigDecimal
) {

    var price: String = ""



}