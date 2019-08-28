package com.raul.androidapps.currencyconverter

import androidx.test.platform.app.InstrumentationRegistry
import com.raul.androidapps.currencyconverter.debug.AppComponentForTest
import it.cosenonjaviste.daggermock.DaggerMock
import testclasses.AppProvidesModuleForTest


fun espressoDaggerMockRule() = DaggerMock.rule<AppComponentForTest>(AppProvidesModuleForTest()) {
    set { component -> component.inject(app) }
    customizeBuilder<AppComponentForTest.Builder> { it.application(app) }
}

val app: CurrencyConverterApplication
    get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as CurrencyConverterApplication