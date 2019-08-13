package com.raul.androidapps.softwaretestrevolut.ui.common

import com.raul.androidapps.softwaretestrevolut.databinding.BindingComponent
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    @Inject
    protected lateinit var bindingComponent: BindingComponent

}