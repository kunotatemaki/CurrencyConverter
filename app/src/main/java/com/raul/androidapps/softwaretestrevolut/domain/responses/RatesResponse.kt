package com.raul.androidapps.softwaretestrevolut.domain.responses

import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.raul.androidapps.softwaretestrevolut.domain.deserializer.RatesDeserializer

@JsonAdapter(RatesDeserializer::class)
data class RatesResponse constructor(
    val base: String,
    val date: String,
    val rates: Map<String, Double>?

)
