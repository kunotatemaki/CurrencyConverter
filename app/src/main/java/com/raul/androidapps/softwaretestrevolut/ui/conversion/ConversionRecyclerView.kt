package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import java.math.BigDecimal

class ConversionRecyclerView (context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    fun updatePriceViewsWithoutRepainting(list: List<SingleRate>, basePrice: BigDecimal){
        if(list.isEmpty()) return
        list.subList(1, list.lastIndex).forEach{singleRate->
            this.findViewWithTag<EditText>(singleRate.code)?.let {view->
                view.setText(singleRate.calculatePrice(basePrice))
            }
        }
    }
}