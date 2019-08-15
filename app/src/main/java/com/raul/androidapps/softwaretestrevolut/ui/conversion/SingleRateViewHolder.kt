package com.raul.androidapps.softwaretestrevolut.ui.conversion

import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate


class SingleRateViewHolder(private val binding: RateItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: SingleRate,
        basePriceListener: BasePriceListener
    ) {
        binding.currencyPriceEditable.removeTextChangedListener(basePriceListener.textWatcher)
        binding.code = item.code
        binding.price = if(item.isBasePrice){
            basePriceListener.getBasePrice()
        }else{
            item.price
        }
        binding.isBasePrice = item.isBasePrice
        binding.currencyPriceNonEditable.tag = item.code
        binding.executePendingBindings()
        if (item.isBasePrice) {
            binding.currencyPriceEditable?.apply {
                addTextChangedListener(basePriceListener.textWatcher)
                setSelection(this.text.length)
            }
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener{
                val price = binding.currencyPriceNonEditable.text.toString()
                basePriceListener.onItemClicked(item.code, price)
            }
        }


    }
}