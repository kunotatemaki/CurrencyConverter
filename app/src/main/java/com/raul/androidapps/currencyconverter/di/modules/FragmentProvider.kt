package com.raul.androidapps.currencyconverter.di.modules

import com.raul.androidapps.currencyconverter.di.interfaces.CustomScopes
import com.raul.androidapps.currencyconverter.ui.conversion.ConversionFragment
import com.raul.androidapps.currencyconverter.ui.selector.SelectorFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Suppress("unused")
@Module
abstract class FragmentsProvider {

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesConversionFragmentFactory(): ConversionFragment

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesSelectorFragmentFactory(): SelectorFragment

}