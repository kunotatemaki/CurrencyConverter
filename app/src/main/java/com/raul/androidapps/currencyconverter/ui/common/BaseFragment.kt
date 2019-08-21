package com.raul.androidapps.currencyconverter.ui.common

import com.raul.androidapps.currencyconverter.databinding.BindingComponent
import com.raul.androidapps.currencyconverter.resources.ResourcesManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    @Inject
    protected lateinit var bindingComponent: BindingComponent

    @Inject
    protected lateinit var resourcesManager: ResourcesManager
}