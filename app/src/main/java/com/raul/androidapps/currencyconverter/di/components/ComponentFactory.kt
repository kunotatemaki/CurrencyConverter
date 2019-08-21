package com.raul.androidapps.currencyconverter.di.components

import com.raul.androidapps.currencyconverter.CurrencyConverterApplication


object ComponentFactory {

    fun component(context: CurrencyConverterApplication): AppComponent {
        return DaggerAppComponent.builder()
                .application(context)
                .build()
    }

}