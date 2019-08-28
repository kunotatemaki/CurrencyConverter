package testclasses

import android.content.Context
import com.raul.androidapps.currencyconverter.CurrencyConverterApplication
import com.raul.androidapps.currencyconverter.network.NetworkServiceFactory
import com.raul.androidapps.currencyconverter.network.NetworkServiceFactoryImpl
import com.raul.androidapps.currencyconverter.preferences.PreferencesManager
import com.raul.androidapps.currencyconverter.preferences.PreferencesManagerImpl
import com.raul.androidapps.currencyconverter.repository.Repository
import com.raul.androidapps.currencyconverter.repository.RepositoryImpl
import com.raul.androidapps.currencyconverter.resources.ResourcesManager
import com.raul.androidapps.currencyconverter.resources.ResourcesManagerImpl
import com.raul.androidapps.currencyconverter.security.Encryption
import com.raul.androidapps.currencyconverter.security.EncryptionImpl
import com.raul.androidapps.currencyconverter.ui.conversion.CoroutineViewModel
import com.raul.androidapps.currencyconverter.ui.conversion.RxJavaViewModel
import dagger.Module
import dagger.Provides


@Suppress("unused")
@Module
open class AppModuleForTest {

    @Provides
    open fun providesContext(application: CurrencyConverterApplication): Context =
        application.applicationContext

    @Provides
    open fun coroutineViewModel(repository: Repository): CoroutineViewModel =
        CoroutineViewModel(repository)

    @Provides
    open fun rxJavaViewModel(repository: Repository): RxJavaViewModel = RxJavaViewModel(repository)

    @Provides
    open fun provideResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager =
        resourcesManagerImpl

    @Provides
    open fun provideNetworkServiceFactory(networkServiceFactoryImp: NetworkServiceFactoryImpl): NetworkServiceFactory =
        networkServiceFactoryImp

    @Provides
    open fun provideEncryption(encryptionImpl: EncryptionImpl): Encryption = encryptionImpl

    @Provides
    open fun providePreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager =
        preferencesManagerImpl

    @Provides
    open fun provideRepository(repositoryImpl: RepositoryImpl): Repository = repositoryImpl


}