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
import com.raul.androidapps.softwaretestrevolut.resources.ResourcesManager


class ConversionAdapter(
    private val basePriceListener: BasePriceListener,
    private val resourcesManager: ResourcesManager,
    private val bindingComponent: BindingComponent
) :
    RecyclerView.Adapter<SingleRateViewHolder>(), RatesDragDrop {

    private var rates: MutableList<SingleRate?> = mutableListOf()

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



    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition >= rates.size || toPosition >= rates.size) return
        rates[fromPosition] = rates[toPosition].also { rates[toPosition] = rates[fromPosition] }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun resetBasePrice() {
        rates[0]?.isBasePrice = true
        rates[1]?.isBasePrice = false
        notifyItemRangeChanged(0,2)
    }

//    fun onItemMoved(fromPosition: Int) {
//        if (fromPosition >= rates.size) return
//        for (from in fromPosition downTo  1){
//            rates[from] = rates[from -1].also { rates[from -1] = rates[from] }
//        }
//        rates[1].isBasePrice = false
//        rates[0].isBasePrice = true
//        notifyItemMoved(fromPosition, 0)
//        notifyItemChanged(1)
//        notifyItemChanged(0)
//    }

    fun test(position: Int){
        rates.add(0, null)
        notifyItemRangeChanged(0, position)
    }

    fun test2(position: Int){
        rates.removeAt(0)
//        if (fromPosition >= rates.size || toPosition >= rates.size) return
        val aux = rates[position]
        for(n in position downTo 1){
            rates[n] = rates[n-1]
        }
        rates[0] = aux
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SingleRateViewHolder, position: Int) {
        holder.bind(rates[position], basePriceListener)
    }

    fun hasSameBaseCurrency(newBaseCurrency: SingleRate?): Boolean {
        if (itemCount == 0) return false
        val oldBaseCurrency = rates.firstOrNull()
        return oldBaseCurrency != null && newBaseCurrency != null && oldBaseCurrency == newBaseCurrency
    }
    var aux = false
    fun submitList(list: List<SingleRate>){
        if(aux.not()) {
        rates = list.toMutableList()
            aux = true
            notifyDataSetChanged()
        }
    }

//    class SingleRateDiffCallback : DiffUtil.ItemCallback<SingleRate>() {
//        override fun areItemsTheSame(oldItem: SingleRate, newItem: SingleRate): Boolean {
//            return oldItem.code == newItem.code
//        }
//
//        override fun areContentsTheSame(oldItem: SingleRate, newItem: SingleRate): Boolean {
//            return oldItem.code == newItem.code && oldItem.isBasePrice == newItem.isBasePrice
//        }
//    }

}