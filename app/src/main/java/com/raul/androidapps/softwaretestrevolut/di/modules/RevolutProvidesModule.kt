package com.raul.androidapps.softwaretestrevolut.di.modules

import android.content.Context
import com.raul.androidapps.softwaretestrevolut.RevolutApplication
import dagger.Module
import dagger.Provides

@Module(includes = [(ViewModelModule::class)])
class RevolutProvidesModule {


    @Provides
    fun providesContext(application: RevolutApplication): Context = application.applicationContext


}