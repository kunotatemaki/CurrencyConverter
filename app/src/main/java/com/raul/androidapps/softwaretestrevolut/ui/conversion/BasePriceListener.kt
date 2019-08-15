package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.text.Editable
import android.text.TextWatcher

abstract class BasePriceListener {
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
            updateBasePrice(p0.toString())

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    abstract fun updateBasePrice(basePrice: String)
    abstract fun getBasePrice(): String

}