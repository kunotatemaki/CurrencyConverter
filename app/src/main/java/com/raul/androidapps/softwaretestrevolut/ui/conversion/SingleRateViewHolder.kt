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
        item: SingleRate?,
        basePriceListener: BasePriceListener
    ) {

            binding.currencyPriceEditable.removeTextChangedListener(basePriceListener.textWatcher)
            binding.code = item?.code
            if (item?.isBasePrice == true) {
                binding.price = basePriceListener.getBasePrice()
                binding.textColor = resourcesManager.getColor(R.color.colorPrimary)
                binding.currencyPriceEditable.findFocus()
            } else {
                binding.textColor = resourcesManager.getColor(android.R.color.darker_gray)
                binding.price = item?.price
            }
            binding.isBasePrice = item?.isBasePrice
            binding.currencyPriceNonEditable.tag = item?.code
            binding.executePendingBindings()
            if (item?.isBasePrice == true) {
                binding.currencyPriceEditable?.apply {
                    addTextChangedListener(basePriceListener.textWatcher)
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
                    val position = if(adapterPosition != RecyclerView.NO_POSITION) adapterPosition else layoutPosition
                    basePriceListener.onItemClicked(this.itemView, item?.code, price, position)
                }
            }

    }
}