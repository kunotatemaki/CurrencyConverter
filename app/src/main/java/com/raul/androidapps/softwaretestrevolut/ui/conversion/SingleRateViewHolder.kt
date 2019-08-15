package com.raul.androidapps.softwaretestrevolut.ui.conversion

import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate


class SingleRateViewHolder(private val binding: RateItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        position: Int,
        item: SingleRate,
        basePriceListener: BasePriceListener
    ) {
        binding.currencyPrice.removeTextChangedListener(basePriceListener.textWatcher)
        binding.singleRate = item
        binding.basePrice = basePriceListener.basePrice
        binding.currencyPrice.tag = item.code
        binding.executePendingBindings()
        if (position == 0) {
            binding.currencyPrice?.apply {
                addTextChangedListener(basePriceListener.textWatcher)
                setSelection(this.text.length)
            }
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener(null)
        }


    }
}