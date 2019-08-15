package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.BindingComponent
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate


class ConversionAdapter(
    private val basePriceListener: BasePriceListener,
    private val bindingComponent: BindingComponent
) :
    ListAdapter<SingleRate, SingleRateViewHolder>(SingleRateDiffCallback()) {

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
        return SingleRateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleRateViewHolder, position: Int) {
        holder.bind(position, getItem(position), basePriceListener)
    }

    fun hasSameBaseCurrency(list: List<SingleRate>): Boolean {
        if (itemCount == 0) return false
        val newBaseCurrency = list.firstOrNull()
        val oldBaseCurrency = getItem(0)
        return oldBaseCurrency != null && newBaseCurrency != null && oldBaseCurrency == newBaseCurrency
    }

    class SingleRateDiffCallback : DiffUtil.ItemCallback<SingleRate>() {
        override fun areItemsTheSame(oldItem: SingleRate, newItem: SingleRate): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: SingleRate, newItem: SingleRate): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

}