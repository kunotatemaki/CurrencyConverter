package com.raul.androidapps.softwaretestrevolut.domain.deserializer

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.raul.androidapps.softwaretestrevolut.domain.responses.RatesResponse
import java.lang.reflect.Type

class RatesDeserializer : JsonDeserializer<RatesResponse> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RatesResponse {
        var base = ""
        var date = ""
        var rates:  Map<String, Double>? = null

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
                rates = Gson().fromJson(jsonObject.get("rates"), type)
            }

        }
        return RatesResponse(base, date, rates)
    }

}
