package com.raul.androidapps.currencyconverter

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.raul.androidapps.currencyconverter.domain.model.BooleanKey
import com.raul.androidapps.currencyconverter.domain.model.Rates
import com.raul.androidapps.currencyconverter.repository.Repository
import com.raul.androidapps.currencyconverter.ui.MainActivity
import com.raul.androidapps.currencyconverter.ui.conversion.ConversionFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*


@RunWith(AndroidJUnit4::class)
class ConversionTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Rule
    @JvmField
    var rule = espressoDaggerMockRule()

//    @Mock
//    private lateinit var viewModel: CoroutineViewModel

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var mockBooleanKey: BooleanKey

    private val testRates: MutableLiveData<Rates> = MutableLiveData()

    @Before
    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        whenever(viewModel.startFetchingRatesAsync())
//            .then {
//                testRates.postValue(
//                    Rates(
//                        mutableListOf(
//                            SingleRate("EUR", 1.toBigDecimal(), true),
//                            SingleRate("USD", 0.90.toBigDecimal(), false),
//                            SingleRate("GBP", 1.12.toBigDecimal(), false)
//                        )
//                    )
//                )
//            }

//        whenever(viewModel.getRates())
//            .thenReturn(testRates)
//        whenever(viewModel.changeCurrency(ArgumentMatchers.anyString()))
//            .thenCallRealMethod()
//        whenever(viewModel.setRates(ArgumentMatchers.anyList()))
//            .then { }

    }

    @Test
    fun testConversionFragmentClickBaseCurrencyDoesNothing() {
        `when`(mockBooleanKey.name()).thenReturn("albert")
        runBlocking {
            //            whenever(repository.getRatesWithCoroutines("EUR"))
//                .thenReturn(
//                    Resource.success(
//                        Rates(
//                            mutableListOf(
//                                SingleRate("EUR", 1.toBigDecimal(), true),
//                                SingleRate("USD", 0.90.toBigDecimal(), false),
//                                SingleRate("GBP", 1.12.toBigDecimal(), false)
//                            )
//                        )
//                    )
//                )
            val activity = activityRule.launchActivity(null)
            val fragment = ConversionFragment()
//            fragment.viewModel = viewModel

            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment, "")
            transaction.commit()

//            onView(withId(R.id.ratesList)).perform(
//                actionOnItemAtPosition<SingleRateViewHolder>(0, click())
//            )
            delay(100000)
            verify(fragment.viewModel, never()).changeCurrency(ArgumentMatchers.anyString())
        }
    }

//    @Test
//    fun testConversionFragmentClickUSDRowChangeToUSDCurrency() {
//
//        val activity = activityRule.activity
//        val fragment = ConversionFragment()
//        fragment.viewModel = viewModel
//
//        val transaction = activity.supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment, "")
//        transaction.commit()
//
//        onView(withId(R.id.ratesList)).perform(
//            actionOnItemAtPosition<SingleRateViewHolder>(1, click())
//        )
//        Thread.sleep(1000)
//        verify(viewModel).changeCurrency("USD")
//    }
//
//    @Test
//    fun testConversionFragmentClickGBPRowChangeToGBPCurrency() {
//
//        val activity = activityRule.activity
//        val fragment = ConversionFragment()
//        fragment.viewModel = viewModel
//
//        val transaction = activity.supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment, "")
//        transaction.commit()
//
//        onView(withId(R.id.ratesList)).perform(
//            actionOnItemAtPosition<SingleRateViewHolder>(2, click())
//        )
//        Thread.sleep(1000)
//        verify(viewModel).changeCurrency("GBP")
//    }
}
