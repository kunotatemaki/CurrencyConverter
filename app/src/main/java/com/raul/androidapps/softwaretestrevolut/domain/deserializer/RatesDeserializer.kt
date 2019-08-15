package com.raul.androidapps.softwaretestrevolut.domain.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import java.lang.reflect.Type


class RatesDeserializer : JsonDeserializer<Rates> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Rates? =

        try {
            val listOfRates: MutableList<SingleRate> = mutableListOf()
            val base: String
            if (json.isJsonObject) {
                val jsonObject = json.asJsonObject
                if (jsonObject.get("base").isJsonPrimitive) {
                    base = jsonObject.get("base")?.asString
                        ?: throw JsonParseException("Error parsing response: no base value included")
                } else {
                    throw JsonParseException("Error parsing response: base value not an String")
                }
                listOfRates.add(
                    SingleRate(
                        code = base,
                        rate = 1.toBigDecimal(),
                        isBasePrice = true
                    )
                )
                if (jsonObject.get("rates").isJsonObject) {
                    jsonObject.get("rates").asJsonObject?.let { rates ->
                        listOfRates.addAll(
                            rates.entrySet().map {
                                SingleRate(
                                    code = it.key,
                                    rate = it.value.asBigDecimal,
                                    isBasePrice = false
                                )
                            }
                        )
                    }
                } else {
                    throw JsonParseException("Error parsing response: no rates value included")
                }
                Rates(listOfRates)
            } else {
                throw JsonParseException("Error parsing response: no value included")
            }

        } catch (e: JsonParseException) {
            null
        }

}
