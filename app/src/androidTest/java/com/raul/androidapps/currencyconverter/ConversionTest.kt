package com.raul.androidapps.currencyconverter

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.currencyconverter.domain.model.Rates
import com.raul.androidapps.currencyconverter.domain.model.SingleRate
import com.raul.androidapps.currencyconverter.ui.MainActivity
import com.raul.androidapps.currencyconverter.ui.conversion.ConversionFragment
import com.raul.androidapps.currencyconverter.ui.conversion.CoroutineViewModel
import com.raul.androidapps.currencyconverter.ui.conversion.SingleRateViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4::class)
class ConversionTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Rule
    @JvmField
    var rule = espressoDaggerMockRule()

    @Mock
    private lateinit var viewModel: CoroutineViewModel


    private val testRates: MutableLiveData<Rates> = MutableLiveData()

    @Before
    fun setUp() {
        whenever(viewModel.startFetchingRatesAsync())
            .then {
                testRates.postValue(
                    Rates(
                        mutableListOf(
                            SingleRate("EUR", 1.toBigDecimal(), true),
                            SingleRate("USD", 0.90.toBigDecimal(), false),
                            SingleRate("GBP", 1.12.toBigDecimal(), false)
                        )
                    )
                )
            }

        whenever(viewModel.getRates())
            .thenReturn(testRates)
        whenever(viewModel.changeCurrency(ArgumentMatchers.anyString()))
            .thenCallRealMethod()
        whenever(viewModel.setRates(ArgumentMatchers.anyList()))
            .then { }

    }

    @Test
    fun testConversionFragmentClickBaseCurrencyDoesNothing() {

        val activity = activityRule.launchActivity(null)
        val fragment = ConversionFragment()

        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, "")
        transaction.commit()

        onView(withId(R.id.ratesList)).perform(
            actionOnItemAtPosition<SingleRateViewHolder>(0, click())
        )
        verify(viewModel, never()).changeCurrency(ArgumentMatchers.anyString())

    }

    @Test
    fun testConversionFragmentClickUSDRowChangeToUSDCurrency() {

        val activity = activityRule.launchActivity(null)
        val fragment = ConversionFragment()

        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, "")
        transaction.commit()

        onView(withId(R.id.ratesList)).perform(
            actionOnItemAtPosition<SingleRateViewHolder>(1, click())
        )
        verify(viewModel).changeCurrency("USD")
    }

    @Test
    fun testConversionFragmentClickGBPRowChangeToGBPCurrency() {

        val activity = activityRule.launchActivity(null)
        val fragment = ConversionFragment()

        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, "")
        transaction.commit()

        onView(withId(R.id.ratesList)).perform(
            actionOnItemAtPosition<SingleRateViewHolder>(2, click())
        )
        Thread.sleep(1000)
        verify(viewModel).changeCurrency("GBP")
    }
}
