package com.raul.androidapps.softwaretestrevolut.di.modules

import com.raul.androidapps.softwaretestrevolut.di.interfaces.CustomScopes
import com.raul.androidapps.softwaretestrevolut.ui.conversion.ConversionFragment
import com.raul.androidapps.softwaretestrevolut.ui.selector.SelectorFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Suppress("unused")
@Module
abstract class FragmentsProvider {

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesConversionFragmentFactory(): ConversionFragment

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesSelectorFragmentFactory(): SelectorFragment

}