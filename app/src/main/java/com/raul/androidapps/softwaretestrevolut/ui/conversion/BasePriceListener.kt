package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.text.Editable
import android.text.TextWatcher
import java.math.BigDecimal

class BasePriceListener {
    var basePrice: BigDecimal = 0.toBigDecimal()
    val textWatcher = object : TextWatcher {
        private var editing = false
        override fun afterTextChanged(p0: Editable?) {
            if (!editing) {
                editing = true
                val formatted = p0?.replaceFirst("^0+(?!$)".toRegex(), "")
                if (formatted != p0.toString()) {
                    p0?.clear()
                    p0?.append(formatted)
                }
                if (p0?.firstOrNull() == '.' || p0?.isEmpty() == true) {
                    p0.insert(0, "0")
                }
                editing = false
            }
            basePrice = try {
                p0.toString().toBigDecimal()
            } catch (e: NumberFormatException) {
                0.toBigDecimal()
            }

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

}