package testclasses

import com.raul.androidapps.currencyconverter.CurrencyConverterApplication
import com.raul.androidapps.currencyconverter.di.modules.*
import com.raul.androidapps.currencyconverter.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class), (ActivityBuilder::class), (ViewModelModule::class),
        (AppModuleForTest::class), (FragmentsProvider::class)]
)
interface AppComponentForTest : AndroidInjector<CurrencyConverterApplication> {

    override fun inject(currencyConverterApp: CurrencyConverterApplication)

    @Component.Builder
    interface Builder {
        fun appModuleForTest(appModuleForTest: AppModuleForTest): Builder

        @BindsInstance
        fun application(application: CurrencyConverterApplication): Builder

        fun build(): AppComponentForTest
    }

}