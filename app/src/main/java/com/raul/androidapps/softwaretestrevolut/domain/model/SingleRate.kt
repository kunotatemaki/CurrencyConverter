package com.raul.androidapps.softwaretestrevolut.domain.model

import java.lang.NumberFormatException


data class SingleRate constructor(
    val code: String,
    val rate: Double,
    val price: String
){

    companion object {
        fun calculatePrice(rate: Double, basePrice: String): String {
            val basePriceConverted = try{
                basePrice.toDouble()
            }catch (e: NumberFormatException){
                return ""
            }
            //todo convert to proper format
            return (rate * basePriceConverted).toString()
        }
    }
}