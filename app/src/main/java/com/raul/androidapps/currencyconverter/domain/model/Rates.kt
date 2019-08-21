package com.raul.androidapps.currencyconverter.domain.model

import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.JsonAdapter
import com.raul.androidapps.currencyconverter.domain.deserializer.RatesDeserializer
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@JsonAdapter(RatesDeserializer::class)
data class Rates constructor(
    val list: List<SingleRate>
) {
    fun getListWithCalculatedPrices(basePrice: String, locale: Locale) {
        val basePriceConverted = try {
            basePrice
                .replace(',', '.')
                .toBigDecimal()
        } catch (e: NumberFormatException) {
            0.toBigDecimal()
        }
        list.forEach {
            val price = it.rate * basePriceConverted
            it.price = formatPrice(price, locale)
        }
    }

    companion object {
        @VisibleForTesting
        fun formatPrice(price: BigDecimal, locale: Locale): String =
            String.format(locale, "%.2f", price.setScale(2, RoundingMode.DOWN))
                .replaceFirst(".00".toRegex(), "")
    }

}
