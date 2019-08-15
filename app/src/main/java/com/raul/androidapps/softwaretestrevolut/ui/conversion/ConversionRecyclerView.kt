package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate

class ConversionRecyclerView(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {

    fun updatePriceViewsWithoutRepainting(list: List<SingleRate>) {
        list.forEach { singleRate ->
            this.findViewWithTag<TextView>(singleRate.code)?.text = singleRate.price
        }
    }
}