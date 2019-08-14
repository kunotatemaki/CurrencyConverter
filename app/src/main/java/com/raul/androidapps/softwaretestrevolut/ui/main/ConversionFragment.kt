package com.raul.androidapps.softwaretestrevolut.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.MainFragmentBinding
import com.raul.androidapps.softwaretestrevolut.extensions.nonNull
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseFragment
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseViewModel
import timber.log.Timber

class ConversionFragment : BaseFragment() {

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false, bindingComponent)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //todo decide which one to use (pass as an argument)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CoroutineViewModel::class.java)

        viewModel.getRates()
            .nonNull()
            .observe({lifecycle}){
                val list = it.toListOfSingleRates("100")
                Timber.d("rukia Recibido rates ${it.base}")
        }

        binding.testButton.setOnClickListener {
            viewModel.changeCurrency("GBP")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startFetchingRates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopFetchingRates()
    }



}
