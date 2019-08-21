package com.raul.androidapps.currencyconverter.di.modules

import com.raul.androidapps.currencyconverter.di.interfaces.CustomScopes
import com.raul.androidapps.currencyconverter.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}