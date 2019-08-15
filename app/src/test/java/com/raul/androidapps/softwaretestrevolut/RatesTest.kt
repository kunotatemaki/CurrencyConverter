package com.raul.androidapps.softwaretestrevolut

import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*


class RatesTest {

    private lateinit var rates: Rates
    private val basePrice = 30.6.toBigDecimal()
    private val wrongBasePrice = ""

    @Before
    fun setUp(){
        val list: List<SingleRate> = mutableListOf(
            SingleRate("EUR", 1.toBigDecimal(), true),
            SingleRate("USD", 0.90.toBigDecimal(), false),
            SingleRate("GBP", 1.12.toBigDecimal(), false)
        )
        rates = Rates(list)
    }

    @Test
    fun testCalculatePricesCorrectBasePrice() {
        rates.getListWithCalculatedPrices(basePrice.toString(), Locale.UK)
        assertEquals(rates.list[0].price, "30.60")
        assertEquals(rates.list[1].price, "27.54")
        assertEquals(rates.list[2].price, "34.27")
    }

    @Test
    fun testCalculatePricesWrongFormattedBasePrice() {
        rates.getListWithCalculatedPrices(wrongBasePrice, Locale.UK)
        assertEquals(rates.list[0].price, "0")
    }

    @Test
    fun testFormatPrices() {
        val priceWithoutDecimals = Rates.formatPrice(23.toBigDecimal(), Locale.UK)
        assertEquals(priceWithoutDecimals, "23")
        val priceWithOneDecimal = Rates.formatPrice(23.2.toBigDecimal(), Locale.UK)
        assertEquals(priceWithOneDecimal, "23.20")
        val priceWithTwoDecimals = Rates.formatPrice(23.28.toBigDecimal(), Locale.UK)
        assertEquals(priceWithTwoDecimals, "23.28")
        val priceWithThreeDecimal = Rates.formatPrice(23.229.toBigDecimal(), Locale.UK)
        assertEquals(priceWithThreeDecimal, "23.22")
    }
}
