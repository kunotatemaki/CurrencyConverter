package com.raul.androidapps.currencyconverter.domain.model

open class BooleanKey constructor( string: String) {
    private val test = string
    open fun name(): String = test
}