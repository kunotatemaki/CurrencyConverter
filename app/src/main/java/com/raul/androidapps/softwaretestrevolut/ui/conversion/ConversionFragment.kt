package com.raul.androidapps.softwaretestrevolut.ui.conversion

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
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.ConversionFragmentBinding
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.extensions.nonNull
import com.raul.androidapps.softwaretestrevolut.ui.MainActivity
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseFragment
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseViewModel
import timber.log.Timber


class ConversionFragment : BaseFragment() {


    private lateinit var binding: ConversionFragmentBinding

    @VisibleForTesting
    lateinit var viewModel: BaseViewModel
    private lateinit var adapter: ConversionAdapter

    private var copyBinding: RateItemBinding? = null

    private val animationTime: Long = 250

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
                                )
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
                    )
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
            viewVisibleAfterScrolling: Boolean
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
                        adapter.test2(positionOfSelectedViewInAdapter)
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
//            val dividerItemDecoration = DividerItemDecoration(
//                context, LinearLayoutManager.VERTICAL
//            )
//            addItemDecoration(dividerItemDecoration)
//            recycledViewPool.getRecycledViewCount(0)
//            recycledViewPool.setMaxRecycledViews(0, 0)
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
                if (rates.list.isNotEmpty()) {
                    binding.progressCircular.visibility = View.GONE
                }
                adapter.submitList(rates.list)
//                if (adapter.hasSameBaseCurrency(rates.list.firstOrNull { it.isBasePrice })) {
//                    binding.ratesList.updatePriceViewsWithoutRepainting(rates.list)
//                } else {
//                    smoothScroller.targetPosition = 0
//                    Handler().postDelayed({
//                        (binding.ratesList.layoutManager as? LinearLayoutManager)?.startSmoothScroll(
//                            smoothScroller
//                        )
//                    }, 750)
//
//                }
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
