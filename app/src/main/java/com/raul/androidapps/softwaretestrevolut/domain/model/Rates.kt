package com.raul.androidapps.softwaretestrevolut.domain.model

import com.google.gson.annotations.JsonAdapter
import com.raul.androidapps.softwaretestrevolut.domain.deserializer.RatesDeserializer

@JsonAdapter(RatesDeserializer::class)
data class Rates constructor(
    val base: String,
    val date: String,
    val currencyRates: Map<String, Double>?
) {

    fun toListOfSingleRates(basePrice: String): List<SingleRate> =
        mutableListOf<SingleRate>().also { list ->
            val baseRate = 1.toDouble()
            list.add(SingleRate(base, baseRate, SingleRate.calculatePrice(baseRate, basePrice)))
            list.addAll(
                currencyRates?.toList()?.map {
                    SingleRate(
                        code = it.first,
                        rate = it.second,
                        price = SingleRate.calculatePrice(it.second, basePrice)
                    )
                } ?: listOf())
        }


}
