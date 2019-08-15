package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.ConversionFragmentBinding
import com.raul.androidapps.softwaretestrevolut.extensions.nonNull
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseFragment
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseViewModel

class ConversionFragment : BaseFragment() {


    private lateinit var binding: ConversionFragmentBinding

    private lateinit var viewModel: BaseViewModel
    private lateinit var adapter: ConversionAdapter

    private val basePriceListener: BasePriceListener = object : BasePriceListener() {
        override fun updateBasePrice(basePrice: String) {
            viewModel.basePrice = basePrice
        }

        override fun getBasePrice(): String =
            viewModel.basePrice

        override fun onItemClicked(code: String, basePrice: String) {
            viewModel.changeCurrency(code)
            updateBasePrice(basePrice)
            //todo scroll to top
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.conversion_fragment,
            container,
            false,
            bindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //todo decide which one to use (pass as an argument)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CoroutineViewModel::class.java)
        adapter = ConversionAdapter(basePriceListener, bindingComponent)
        binding.ratesList.adapter = adapter

        viewModel.getRates()
            .nonNull()
            .observe({ lifecycle }) { rates ->
                if (adapter.hasSameBaseCurrency(rates.list.firstOrNull { it.isBasePrice })) {
                    binding.ratesList.updatePriceViewsWithoutRepainting(rates.list)
                }
                adapter.submitList(rates.list)
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
