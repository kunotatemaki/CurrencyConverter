package com.raul.androidapps.currencyconverter

import android.util.Log
import com.raul.androidapps.currencyconverter.di.components.ComponentFactory
import com.raul.androidapps.currencyconverter.di.components.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


class CurrencyConverterApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<CurrencyConverterApplication> {
        val mComponent: AppComponent = ComponentFactory.component(this)
        mComponent.inject(this)
        return mComponent
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Logging with Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }


    }

    /** A tree which logs important information for crash reporting. (Tiber) */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

        }
    }
}
