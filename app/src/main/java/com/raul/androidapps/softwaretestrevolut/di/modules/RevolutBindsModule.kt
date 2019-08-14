package com.raul.androidapps.softwaretestrevolut.di.modules

import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactory
import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactoryImpl
import com.raul.androidapps.softwaretestrevolut.preferences.PreferencesManager
import com.raul.androidapps.softwaretestrevolut.preferences.PreferencesManagerImpl
import com.raul.androidapps.softwaretestrevolut.repository.Repository
import com.raul.androidapps.softwaretestrevolut.repository.RepositoryImpl
import com.raul.androidapps.softwaretestrevolut.resources.ResourcesManager
import com.raul.androidapps.softwaretestrevolut.resources.ResourcesManagerImpl
import com.raul.androidapps.softwaretestrevolut.security.Encryption
import com.raul.androidapps.softwaretestrevolut.security.EncryptionImpl
import dagger.Binds
import dagger.Module


@Module(includes = [(ViewModelModule::class)])
abstract class RevolutBindsModule {


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