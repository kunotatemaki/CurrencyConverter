package com.raul.androidapps.currencyconverter.ui.conversion

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.raul.androidapps.currencyconverter.R
import com.raul.androidapps.currencyconverter.databinding.ConversionFragmentBinding
import com.raul.androidapps.currencyconverter.databinding.RateItemBinding
import com.raul.androidapps.currencyconverter.extensions.nonNull
import com.raul.androidapps.currencyconverter.ui.MainActivity
import com.raul.androidapps.currencyconverter.ui.common.BaseFragment
import com.raul.androidapps.currencyconverter.ui.common.BaseViewModel


class ConversionFragment : BaseFragment() {


    private lateinit var binding: ConversionFragmentBinding

    @VisibleForTesting
    lateinit var viewModel: BaseViewModel
    private lateinit var adapter: ConversionAdapter

    private var copyBinding: RateItemBinding? = null

    private val animationTime: Long = 250
    private var animatingFlag = false

    private enum class MultiThreadingMethod {
        COROUTINES, RX_JAVA
    }


    private var threading: MultiThreadingMethod = MultiThreadingMethod.COROUTINES

    private val basePriceListener: BasePriceListener = object : BasePriceListener() {
        override fun updateBasePrice(basePrice: String) {
            viewModel.basePrice = basePrice
        }

        override fun getBasePrice(): String =
            viewModel.basePrice

        override fun onItemClicked(v: View, code: String?, basePrice: String, position: Int) {
            (activity as? MainActivity)?.hideKeyboard()
            animatingFlag = true

            val verticalScrollOffset = binding.ratesList.computeVerticalScrollOffset()
            val visibleListHeight = binding.ratesList.computeVerticalScrollExtent()

            createCopyOfTheSelectedViewAndAttachToTheScreen(
                positionOfSelectedViewInAdapter = position,
                positionOfSelectedViewOnScreenInPx = v.y
            )
            if (verticalScrollOffset > 0) {
                binding.ratesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int
                    ) {
                        if (newState == SCROLL_STATE_IDLE) {
                            binding.ratesList.removeOnScrollListener(this)
                            scrollAllItemsToAllowCopyViewToBePlacedAtTheTopOfTheScreen(
                                positionOfSelectedViewInAdapter = position
                            )

                            animateCopyViewToTheTopOfTheScreen(
                                copyView = copyBinding?.root,
                                originalView = v,
                                positionOfSelectedViewInAdapter = position,
                                viewVisibleAfterScrolling = wouldTheCopyViewStillVisibleAfterScrollingToTop(
                                    positionOfSelectedViewOnScreenInPx = copyBinding?.root?.y ?: 0F,
                                    heightToBeScrolled = verticalScrollOffset,
                                    heightOfVisibleListOnScreen = visibleListHeight
                                ),
                                basePrice = basePrice
                            )
                        }
                    }
                })
            } else {
                scrollAllItemsToAllowCopyViewToBePlacedAtTheTopOfTheScreen(
                    positionOfSelectedViewInAdapter = position
                )
                animateCopyViewToTheTopOfTheScreen(
                    copyView = copyBinding?.root,
                    originalView = v,
                    positionOfSelectedViewInAdapter = position,
                    viewVisibleAfterScrolling = wouldTheCopyViewStillVisibleAfterScrollingToTop(
                        positionOfSelectedViewOnScreenInPx = copyBinding?.root?.y ?: 0F,
                        heightToBeScrolled = verticalScrollOffset,
                        heightOfVisibleListOnScreen = visibleListHeight
                    ),
                    basePrice = basePrice
                )
            }
            binding.ratesList.smoothScrollBy(0, -verticalScrollOffset)

        }

        private fun createCopyOfTheSelectedViewAndAttachToTheScreen(
            positionOfSelectedViewInAdapter: Int,
            positionOfSelectedViewOnScreenInPx: Float
        ) {
            val inflater = LayoutInflater.from(context)
            copyBinding = DataBindingUtil.inflate<RateItemBinding>(
                inflater,
                R.layout.rate_item,
                binding.main,
                false,
                bindingComponent
            )
            copyBinding?.let {
                adapter.getItem(positionOfSelectedViewInAdapter)?.let { item ->
                    val vHolder = SingleRateViewHolder(it, resourcesManager, true)
                    vHolder.bind(item, null, 0)
                    binding.main.addView(it.root)
                    it.root.y = positionOfSelectedViewOnScreenInPx
                }
            }
        }

        private fun removeCopyViewFromScreen() {
            binding.main.removeView(copyBinding?.root)
        }

        private fun animateCopyViewToTheTopOfTheScreen(
            copyView: View?,
            originalView: View?,
            positionOfSelectedViewInAdapter: Int,
            viewVisibleAfterScrolling: Boolean,
            basePrice: String
        ) {
            if (viewVisibleAfterScrolling) {
                originalView?.visibility = View.INVISIBLE
            }
            copyView?.animate()?.y(0F)?.setDuration(animationTime)
                ?.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {}
                    override fun onAnimationEnd(p0: Animator?) {
                        removeCopyViewFromScreen()
                        if (viewVisibleAfterScrolling) {
                            originalView?.visibility = View.VISIBLE
                        }
                        val newList = adapter.moveRateToTheTop(positionOfSelectedViewInAdapter)
                        viewModel.setRates(newList)
                        if(newList.isNotEmpty())
                        viewModel.changeCurrency(newList.first().code)
                        updateBasePrice(basePrice)
                        animatingFlag = false
                    }
                    override fun onAnimationCancel(p0: Animator?) {}
                    override fun onAnimationStart(p0: Animator?) {}
                })?.start()
        }

        private fun wouldTheCopyViewStillVisibleAfterScrollingToTop(
            positionOfSelectedViewOnScreenInPx: Float,
            heightToBeScrolled: Int,
            heightOfVisibleListOnScreen: Int
        ): Boolean =
            positionOfSelectedViewOnScreenInPx + heightToBeScrolled < heightOfVisibleListOnScreen


        private fun scrollAllItemsToAllowCopyViewToBePlacedAtTheTopOfTheScreen(
            positionOfSelectedViewInAdapter: Int
        ) {
            for (i in 0 until positionOfSelectedViewInAdapter) {
                binding.ratesList.apply {
                    getChildAt(i)?.let {
                        getChildViewHolder(it)?.let { viewHolder ->
                            viewHolder.setIsRecyclable(false)
                            viewHolder.itemView.animate()
                                .y(viewHolder.itemView.y + viewHolder.itemView.height)
                                .setDuration(animationTime)
                                .setListener(null)
                                .start()
                        }
                    }
                }
            }
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
        if (::viewModel.isInitialized.not()) {
            viewModel = when (threading) {
                MultiThreadingMethod.COROUTINES -> ViewModelProviders.of(
                    this,
                    viewModelFactory
                ).get(
                    CoroutineViewModel::class.java
                )
                MultiThreadingMethod.RX_JAVA -> ViewModelProviders.of(
                    this,
                    viewModelFactory
                ).get(
                    RxJavaViewModel::class.java
                )
            }
        }

        adapter = ConversionAdapter(basePriceListener, resourcesManager, bindingComponent)

        binding.ratesList.apply {
            this@apply.adapter = this@ConversionFragment.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    (activity as? MainActivity)?.hideKeyboard()
                }
            })
        }

        smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        viewModel.getRates()
            .nonNull()
            .observe({ lifecycle }) { rates ->
                if(animatingFlag.not()) {
                    if (rates.list.isNotEmpty()) {
                        binding.progressCircular.visibility = View.GONE
                    }
                    val screenRepainted = adapter.submitList(rates.list)
                    if (screenRepainted.not()) {
                        binding.ratesList.updatePriceViewsWithoutRepainting(rates.list)
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startFetchingRatesAsync()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopFetchingRates()
    }


}
