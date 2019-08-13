package com.raul.androidapps.softwaretestrevolut.di.components

import com.raul.androidapps.softwaretestrevolut.RevolutApplication
import com.raul.androidapps.softwaretestrevolut.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class), (ActivityBuilder::class), (RevolutBindsModule::class),
        (RevolutProvidesModule::class), (FragmentsProvider::class), (FragmentsProvider::class), (ViewModelModule::class)]
)
interface RevolutComponent : AndroidInjector<RevolutApplication> {

    override fun inject(revolutApp: RevolutApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: RevolutApplication): Builder

        fun build(): RevolutComponent
    }

}