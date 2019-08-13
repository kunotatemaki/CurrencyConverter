package com.raul.androidapps.softwaretestrevolut.di.modules

import com.raul.androidapps.softwaretestrevolut.di.interfaces.CustomScopes
import com.raul.androidapps.softwaretestrevolut.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}