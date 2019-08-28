package com.raul.androidapps.currencyconverter.di.modules

import android.content.Context
import com.raul.androidapps.currencyconverter.CurrencyConverterApplication
import com.raul.androidapps.currencyconverter.repository.Repository
import com.raul.androidapps.currencyconverter.ui.conversion.CoroutineViewModel
import com.raul.androidapps.currencyconverter.ui.conversion.RxJavaViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [(ViewModelModule::class)])
class AppProvidesModule {

    @Provides
    fun providesContext(application: CurrencyConverterApplication): Context =
        application.applicationContext

    @Provides
    fun coroutineViewModel(repository: Repository): CoroutineViewModel =
        CoroutineViewModel(repository)

    @Provides
    fun rxJavaViewModel(repository: Repository): RxJavaViewModel = RxJavaViewModel(repository)
}