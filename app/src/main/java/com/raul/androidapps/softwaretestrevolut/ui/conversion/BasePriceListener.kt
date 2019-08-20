package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.text.Editable
import android.text.TextWatcher
import android.view.View

abstract class BasePriceListener {
    val textWatcher = object : TextWatcher {
        private var editing = false
        private var textToMaintain: String? = null
        override fun afterTextChanged(p0: Editable?) {
            if (!editing) {
                editing = true
                textToMaintain?.let {
                    copyStringInEditable(p0, it)
                }

                val formatted = p0?.replaceFirst("^0+(?![.,]|$)".toRegex(), "") ?: ""
                if (formatted != p0.toString()) {
                    copyStringInEditable(p0, "formatted")
                }
                if (p0?.firstOrNull() == '.' || p0?.firstOrNull() == ',' || p0?.isEmpty() == true) {
                    p0.insert(0, "0")
                }
                editing = false
            }
            updateBasePrice(p0.toString())

        }

        override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
            textToMaintain = p0.toString()
        }

        override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
            if (textToMaintain?.matches(".*[.,].*".toRegex()) == true) {
                val added = p0.toString().substring(start, start + count)
                if (added.matches(".*[.,].*".toRegex()).not()) {
                    textToMaintain = null
                }
            } else {
                textToMaintain = null
            }
        }

        private fun copyStringInEditable(old: Editable?, new: String) {
            new.forEachIndexed { index, c ->
                if (old?.length ?: 0 > index) {
                    old?.replace(index, index + 1, c.toString())
                }
            }
            if (new.length < old?.length ?: 0) {
                old?.delete(new.length, old.length)
            }
        }
    }

    abstract fun updateBasePrice(basePrice: String)
    abstract fun getBasePrice(): String
    abstract fun onItemClicked(v: View, code: String?, basePrice: String, position: Int)

}