package com.raul.androidapps.currencyconverter.di.components

import com.raul.androidapps.currencyconverter.CurrencyConverterApplication
import com.raul.androidapps.currencyconverter.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class), (ActivityBuilder::class), (AppBindsModule::class),
        (AppProvidesModule::class), (FragmentsProvider::class), (FragmentsProvider::class), (ViewModelModule::class)]
)
interface AppComponent : AndroidInjector<CurrencyConverterApplication> {

    override fun inject(currencyConverterApp: CurrencyConverterApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: CurrencyConverterApplication): Builder

        fun build(): AppComponent
    }

}