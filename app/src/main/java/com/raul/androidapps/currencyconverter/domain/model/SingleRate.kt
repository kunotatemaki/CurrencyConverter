package com.raul.androidapps.currencyconverter.domain.model

import java.math.BigDecimal


data class SingleRate constructor(
    val code: String,
    val rate: BigDecimal,
    var isBasePrice: Boolean
) {

    var price: String = ""

}