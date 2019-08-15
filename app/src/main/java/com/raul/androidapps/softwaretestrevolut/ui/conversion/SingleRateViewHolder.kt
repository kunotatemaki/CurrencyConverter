package com.raul.androidapps.softwaretestrevolut.ui.conversion

import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import com.raul.androidapps.softwaretestrevolut.resources.ResourcesManager


class SingleRateViewHolder(
    private val binding: RateItemBinding,
    private val resourcesManager: ResourcesManager
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: SingleRate,
        basePriceListener: BasePriceListener
    ) {
        binding.currencyPriceEditable.removeTextChangedListener(basePriceListener.textWatcher)
        binding.code = item.code
        if (item.isBasePrice) {
            binding.price = basePriceListener.getBasePrice()
            binding.textColor = resourcesManager.getColor(R.color.colorPrimary)
            binding.currencyPriceEditable?.apply {
                addTextChangedListener(basePriceListener.textWatcher)
                setSelection(this.text.length)
            }
            binding.root.setOnClickListener(null)
        } else {
            binding.textColor = resourcesManager.getColor(android.R.color.darker_gray)
            binding.price = item.price
            binding.root.setOnClickListener {
                val price = binding.currencyPriceNonEditable.text.toString()
                basePriceListener.onItemClicked(item.code, price)
            }
        }
        binding.isBasePrice = item.isBasePrice
        binding.currencyPriceNonEditable.tag = item.code
        binding.executePendingBindings()


    }
}