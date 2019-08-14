package com.raul.androidapps.softwaretestrevolut.domain.deserializer

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import java.lang.reflect.Type

class RatesDeserializer : JsonDeserializer<Rates> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Rates {
        var base = ""
        var date = ""
        var currencyRates:  Map<String, Double>? = null

        if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            if (jsonObject.get("base")?.isJsonPrimitive == true) {
                 base = jsonObject.get("base")?.asString ?: ""
            }
            if (jsonObject.get("date")?.isJsonPrimitive == true) {
                 date = jsonObject.get("date")?.asString ?: ""
            }
            if (jsonObject.get("rates").isJsonObject) {
                val type = object : TypeToken<Map<String, Double>>() {}.type
                currencyRates = Gson().fromJson(jsonObject.get("rates"), type)
            }

        }
        return Rates(base, date, currencyRates)
    }

}
