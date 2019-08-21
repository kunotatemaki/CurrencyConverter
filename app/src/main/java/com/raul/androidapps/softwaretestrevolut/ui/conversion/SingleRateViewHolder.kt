package com.raul.androidapps.softwaretestrevolut.ui.conversion

import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import com.raul.androidapps.softwaretestrevolut.resources.ResourcesManager
import timber.log.Timber
import java.text.FieldPosition


class SingleRateViewHolder(
    private val binding: RateItemBinding,
    private val resourcesManager: ResourcesManager
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: SingleRate,
        basePriceListener: BasePriceListener,
        position: Int
    ) {
//        itemView.y = 0f
//        itemView.setBackgroundColor(when(position){
//            0->resourcesManager.getColor(android.R.color.holo_orange_dark)
//            1->resourcesManager.getColor(R.color.colorPrimaryDark)
//            2->resourcesManager.getColor(R.color.colorAccent)
//            3->resourcesManager.getColor(android.R.color.holo_green_light)
//            6->resourcesManager.getColor(android.R.color.holo_blue_light)
//            else->resourcesManager.getColor(android.R.color.white)
//        })
//        Timber.d("rukia position $positionTest -> y = ${itemView.y}")
            binding.currencyPriceEditable.removeTextChangedListener(basePriceListener.textWatcher)
            binding.code = item.code
            if (item.isBasePrice) {
                binding.price = basePriceListener.getBasePrice()
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
                    basePriceListener.onItemClicked(this.itemView, item.code, price, position)
                }
            }

    }
}