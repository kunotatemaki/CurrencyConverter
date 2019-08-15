package com.raul.androidapps.softwaretestrevolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactory
import com.raul.androidapps.softwaretestrevolut.network.Resource
import com.raul.androidapps.softwaretestrevolut.network.RevolutApi
import com.raul.androidapps.softwaretestrevolut.repository.Repository
import com.raul.androidapps.softwaretestrevolut.ui.conversion.CoroutineViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class ViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkServiceFactory: NetworkServiceFactory

    @Mock
    lateinit var api: RevolutApi

    @Mock
    lateinit var repository: Repository

    private lateinit var oldRates: Rates
    private lateinit var newRates: Rates
    private lateinit var coroutineViewModel: CoroutineViewModel
    private val currency = "EUR"


    @Before
    fun setUp() {
        val oldList: List<SingleRate> = mutableListOf(
            SingleRate("EUR", 1.toBigDecimal(), true),
            SingleRate("USD", 0.90.toBigDecimal(), false),
            SingleRate("GBP", 1.12.toBigDecimal(), false)
        )
        oldRates = Rates(oldList)
        val newList: List<SingleRate> = mutableListOf(
            SingleRate("GBP", 1.toBigDecimal(), true),
            SingleRate("USD", 1.15.toBigDecimal(), false),
            SingleRate("EUR", 0.93.toBigDecimal(), false)
        )
        newRates = Rates(newList)
        MockitoAnnotations.initMocks(this)
        coroutineViewModel = CoroutineViewModel(repository)

        Mockito.`when`(networkServiceFactory.getServiceInstance())
            .thenReturn(
                api
            )

    }

    @Test
    fun testOrderNewRatesWithPreviousOnes() {
        runBlocking {
            val firstJob = coroutineViewModel.updateObservableAsync(oldRates)
            firstJob.join()
            val ratesBefore = coroutineViewModel.getRates().getItem()
            assertEquals(ratesBefore.list[0].code, "EUR")
            assertTrue(ratesBefore.list[0].isBasePrice)
            assertEquals(ratesBefore.list[1].code, "USD")
            assertEquals(ratesBefore.list[2].code, "GBP")
            val secondJob = coroutineViewModel.updateObservableAsync(newRates)
            secondJob.join()
            val ratesLater = coroutineViewModel.getRates().getItem()
            assertEquals(ratesLater.list[0].code, "GBP")
            assertTrue(ratesLater.list[0].isBasePrice)
            assertEquals(ratesLater.list[1].code, "EUR")
            assertEquals(ratesLater.list[2].code, "USD")
        }
    }

    @Test
    fun testCallRepository() {
        runBlocking {
            Mockito.`when`(api.getLatestRatesWithCoroutines(currency))
                .thenReturn(
                    Response.success(newRates)
                )
            Mockito.`when`(repository.getRates(currency))
                .thenReturn(
                    Resource.success(newRates)
                )
            val job = coroutineViewModel.startFetchingRatesAsync(currency)

            Mockito.verify(repository).getRates(currency)
        }
    }
}
