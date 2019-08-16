package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.ConversionFragmentBinding
import com.raul.androidapps.softwaretestrevolut.databinding.MainActivityBinding
import com.raul.androidapps.softwaretestrevolut.extensions.nonNull
import com.raul.androidapps.softwaretestrevolut.ui.MainActivity
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseFragment
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseViewModel


class ConversionFragment : BaseFragment() {


    private lateinit var binding: ConversionFragmentBinding

    private lateinit var viewModel: BaseViewModel
    private lateinit var adapter: ConversionAdapter

    private enum class MultiThreadingMethod {
        COROUTINES, RX_JAVA
    }

    private lateinit var threading: MultiThreadingMethod

    private val basePriceListener: BasePriceListener = object : BasePriceListener() {
        override fun updateBasePrice(basePrice: String) {
            viewModel.basePrice = basePrice
        }

        override fun getBasePrice(): String =
            viewModel.basePrice

        override fun onItemClicked(code: String, basePrice: String) {
            (activity as? MainActivity)?.hideKeyboard()
            viewModel.changeCurrency(code)
            updateBasePrice(basePrice)
        }

    }

    private lateinit var smoothScroller: LinearSmoothScroller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val safeArgs = ConversionFragmentArgs.fromBundle(this)
            threading = if (safeArgs.coroutines) {
                MultiThreadingMethod.COROUTINES
            } else {
                MultiThreadingMethod.RX_JAVA
            }
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
        viewModel = when (threading) {
            MultiThreadingMethod.COROUTINES -> ViewModelProviders.of(this, viewModelFactory).get(
                CoroutineViewModel::class.java
            )
            MultiThreadingMethod.RX_JAVA -> ViewModelProviders.of(this, viewModelFactory).get(
                RxJavaViewModel::class.java
            )
        }

        adapter = ConversionAdapter(basePriceListener, resourcesManager, bindingComponent)

        binding.ratesList.apply {
            this@apply.adapter = this@ConversionFragment.adapter
            val dividerItemDecoration = DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
        }
        smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        viewModel.getRates()
            .nonNull()
            .observe({ lifecycle }) { rates ->
                if (rates.list.isNotEmpty()) {
                    binding.progressCircular.visibility = View.GONE
                }
                adapter.submitList(rates.list)
                if (adapter.hasSameBaseCurrency(rates.list.firstOrNull { it.isBasePrice })) {
                    binding.ratesList.updatePriceViewsWithoutRepainting(rates.list)
                } else {
                    smoothScroller.targetPosition = 0
                    Handler().postDelayed({
                        (binding.ratesList.layoutManager as? LinearLayoutManager)?.startSmoothScroll(
                            smoothScroller
                        )
                    }, 750)

                }
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
