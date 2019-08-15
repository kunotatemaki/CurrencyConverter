package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.BindingComponent
import com.raul.androidapps.softwaretestrevolut.databinding.RateItemBinding
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import java.math.BigDecimal


class ConversionAdapter(private val clickListener: (String) -> Unit, private val bindingComponent: BindingComponent) :
    ListAdapter<SingleRate, ConversionAdapter.SingleRateViewHolder>(SingleRateDiffCallback()) {

    private var basePrice: BigDecimal = 100.toBigDecimal()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<RateItemBinding>(inflater, R.layout.rate_item, parent, false, bindingComponent)
        return SingleRateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleRateViewHolder, position: Int) {
        holder.bind(getItem(position), basePrice, clickListener)
    }

    class SingleRateViewHolder(private val binding: RateItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SingleRate, basePrice: BigDecimal, clickListener: (String) -> Unit) {
            binding.singleRate = item
            binding.basePrice = basePrice
            binding.executePendingBindings()
            itemView.setOnClickListener { clickListener(item.code) }
        }
    }

    class SingleRateDiffCallback : DiffUtil.ItemCallback<SingleRate>() {
        override fun areItemsTheSame(oldItem: SingleRate, newItem: SingleRate): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: SingleRate, newItem: SingleRate): Boolean {
            return oldItem == newItem
        }
    }

}