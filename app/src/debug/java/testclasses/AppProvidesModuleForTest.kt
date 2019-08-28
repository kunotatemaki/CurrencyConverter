package testclasses

import android.content.Context
import com.raul.androidapps.currencyconverter.CurrencyConverterApplication
import com.raul.androidapps.currencyconverter.domain.model.BooleanKey
import dagger.Module
import dagger.Provides


@Module
class AppProvidesModuleForTest {

    @Provides
    fun providesContext(application: CurrencyConverterApplication): Context =
        application.applicationContext

    @Provides
    fun provideABCKey(): BooleanKey = BooleanKey(
        "test"
    )

}