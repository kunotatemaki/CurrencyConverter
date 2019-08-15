package com.raul.androidapps.softwaretestrevolut.domain.model

import com.google.gson.annotations.JsonAdapter
import com.raul.androidapps.softwaretestrevolut.domain.deserializer.RatesDeserializer

@JsonAdapter(RatesDeserializer::class)
data class Rates constructor(
    val list: List<SingleRate>
)
