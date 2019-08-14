package com.raul.androidapps.softwaretestrevolut.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raul.androidapps.softwaretestrevolut.di.interfaces.ViewModelKey
import com.raul.androidapps.softwaretestrevolut.ui.common.ViewModelFactory
import com.raul.androidapps.softwaretestrevolut.ui.main.CoroutineViewModel
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
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}