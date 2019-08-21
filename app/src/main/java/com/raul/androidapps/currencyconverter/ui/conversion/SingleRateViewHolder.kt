package com.raul.androidapps.currencyconverter.ui.conversion

import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.currencyconverter.R
import com.raul.androidapps.currencyconverter.databinding.RateItemBinding
import com.raul.androidapps.currencyconverter.domain.model.SingleRate
import com.raul.androidapps.currencyconverter.resources.ResourcesManager


class SingleRateViewHolder(
    private val binding: RateItemBinding,
    private val resourcesManager: ResourcesManager,
    private val copyView: Boolean = false
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: SingleRate,
        basePriceListener: BasePriceListener?,
        position: Int
    ) {
        if (copyView) {
            itemView.setBackgroundColor(resourcesManager.getColor(R.color.selected_item))
        }
        binding.currencyPriceEditable.removeTextChangedListener(basePriceListener?.textWatcher)
        binding.code = item.code
        if (item.isBasePrice) {
            binding.price = basePriceListener?.getBasePrice()
            binding.textColor = resourcesManager.getColor(R.color.colorPrimary)
            binding.currencyPriceEditable.findFocus()
        } else {
            binding.textColor = resourcesManager.getColor(android.R.color.darker_gray)
            binding.price = item.price
        }
        binding.isBasePrice = item.isBasePrice
        binding.currencyPriceNonEditable.tag = item.code
        binding.executePendingBindings()
        if (item.isBasePrice) {
            binding.currencyPriceEditable?.apply {
                addTextChangedListener(basePriceListener?.textWatcher)
                setSelection(this.text.length)
            }
            binding.root.apply {
                setOnClickListener(null)
                isClickable = false
                isFocusable = false
            }
        } else {
            binding.root.setOnClickListener {
                val price = binding.currencyPriceNonEditable.text.toString()
                basePriceListener?.onItemClicked(this.itemView, item.code, price, position)
            }
        }

    }
}