package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.BindingComponent
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import com.raul.androidapps.softwaretestrevolut.resources.ResourcesManager


class ConversionAdapter(
    private val basePriceListener: BasePriceListener,
    private val resourcesManager: ResourcesManager,
    private val bindingComponent: BindingComponent
) :
    RecyclerView.Adapter<SingleRateViewHolder>() {

    private var rates: MutableList<SingleRate> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<RateItemBinding>(
                inflater,
                R.layout.rate_item,
                parent,
                false,
                bindingComponent
            )
        return SingleRateViewHolder(binding, resourcesManager)
    }

    override fun getItemCount(): Int = rates.size

    fun moveRateToTheTop(position: Int): List<SingleRate> {
        val aux = rates[position]
        for (n in position downTo 1) {
            rates[n] = rates[n - 1]
        }
        rates[0] = aux
        rates[0].isBasePrice = true
        rates[1].isBasePrice = false
        notifyDataSetChanged()
        return rates
    }

    override fun onBindViewHolder(holder: SingleRateViewHolder, position: Int) {
        holder.bind(rates[position], basePriceListener, position)
    }

    fun getItem(position: Int): SingleRate? {
        return if (position < rates.size) {
            rates[position]
        } else {
            null
        }
    }

    fun hasSameBaseCurrency(newBaseCurrency: SingleRate?): Boolean {
        if (itemCount == 0) return false
        val oldBaseCurrency = rates.firstOrNull()
        return oldBaseCurrency != null && newBaseCurrency != null && oldBaseCurrency == newBaseCurrency
    }

    fun submitList(list: List<SingleRate>): Boolean {
        if (list.isNotEmpty()) {
            if (hasSameBaseCurrency(list.first()).not()) {
                rates = list.toMutableList()
                notifyDataSetChanged()
                return true
            }
        }
        return false
    }

}