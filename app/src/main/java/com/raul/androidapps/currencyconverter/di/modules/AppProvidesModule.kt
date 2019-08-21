package com.raul.androidapps.currencyconverter.di.modules

import android.content.Context
import com.raul.androidapps.currencyconverter.CurrencyConverterApplication
import dagger.Module
import dagger.Provides

@Module(includes = [(ViewModelModule::class)])
class AppProvidesModule {


    @Provides
    fun providesContext(application: CurrencyConverterApplication): Context = application.applicationContext


}