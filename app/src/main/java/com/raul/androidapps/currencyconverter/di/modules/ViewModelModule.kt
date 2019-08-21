package com.raul.androidapps.currencyconverter.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raul.androidapps.currencyconverter.di.interfaces.ViewModelKey
import com.raul.androidapps.currencyconverter.ui.common.ViewModelFactory
import com.raul.androidapps.currencyconverter.ui.conversion.CoroutineViewModel
import com.raul.androidapps.currencyconverter.ui.conversion.RxJavaViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap



@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoroutineViewModel::class)
    internal abstract fun bindCoroutineViewModel(coroutineViewModel: CoroutineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RxJavaViewModel::class)
    internal abstract fun bindRxJavaViewModel(rxJavaViewModel: RxJavaViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}