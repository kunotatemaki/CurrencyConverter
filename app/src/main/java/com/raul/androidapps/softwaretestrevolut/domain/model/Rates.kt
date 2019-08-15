package com.raul.androidapps.softwaretestrevolut.domain.model

import com.google.gson.annotations.JsonAdapter
import com.raul.androidapps.softwaretestrevolut.domain.deserializer.RatesDeserializer
import java.util.*

@JsonAdapter(RatesDeserializer::class)
data class Rates constructor(
    val list: List<SingleRate>
) {
    fun getListWithCalculatedPrices(basePrice: String) {
        val basePriceConverted = try {
            basePrice.toBigDecimal()
        } catch (e: NumberFormatException) {
            0.toBigDecimal()
        }
        list.forEach {
            val price = it.rate * basePriceConverted
            it.price = String.format(Locale.getDefault(), "%.2f", price)
                .replaceFirst(".00".toRegex(), "")
        }
    }

}
