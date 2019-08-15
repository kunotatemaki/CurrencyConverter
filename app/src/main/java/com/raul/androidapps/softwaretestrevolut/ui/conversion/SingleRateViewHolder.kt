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
        binding.currencyPriceEditable.removeTextChangedListener(basePriceListener.textWatcher)
        binding.code = item.code
        val isBasePrice = position == 0
        binding.price = if(isBasePrice){
            basePriceListener.getBasePrice()
        }else{
            item.price
        }
        binding.isBasePrice = position == 0
        binding.currencyPriceNonEditable.tag = item.code
        binding.executePendingBindings()
        if (position == 0) {
            binding.currencyPriceEditable?.apply {
                addTextChangedListener(basePriceListener.textWatcher)
                setSelection(this.text.length)
            }
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener(null)
        }


    }
}