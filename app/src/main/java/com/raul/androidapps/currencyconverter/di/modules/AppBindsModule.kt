package com.raul.androidapps.currencyconverter.di.modules

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
import dagger.Binds
import dagger.Module


@Module(includes = [(ViewModelModule::class)])
abstract class AppBindsModule {


    @Binds
    abstract fun provideResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager

    @Binds
    abstract fun provideNetworkServiceFactory(networkServiceFactoryImp: NetworkServiceFactoryImpl): NetworkServiceFactory

    @Binds
    abstract fun provideEncryption(encryptionImpl: EncryptionImpl): Encryption

    @Binds
    abstract fun providePreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager

    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository

}