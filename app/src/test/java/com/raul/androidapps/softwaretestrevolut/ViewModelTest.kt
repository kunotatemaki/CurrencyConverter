package com.raul.androidapps.softwaretestrevolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactory
import com.raul.androidapps.softwaretestrevolut.network.Resource
import com.raul.androidapps.softwaretestrevolut.network.RevolutApi
import com.raul.androidapps.softwaretestrevolut.repository.Repository
import com.raul.androidapps.softwaretestrevolut.ui.conversion.CoroutineViewModel
import com.raul.androidapps.softwaretestrevolut.ui.conversion.RxJavaViewModel
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeast
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
    private lateinit var rxJavaViewModel: RxJavaViewModel
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
        rxJavaViewModel = RxJavaViewModel(repository)

        Mockito.`when`(networkServiceFactory.getServiceInstance())
            .thenReturn(
                api
            )
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

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
            Mockito.`when`(repository.getRatesWithCoroutines(currency))
                .thenReturn(
                    Resource.success(newRates)
                )
            coroutineViewModel.changeCurrency(currency)
            coroutineViewModel.startFetchingRatesAsync()
            delay(2000)

            Mockito.verify(repository, atLeast(1)).getRatesWithCoroutines(currency)
        }
    }

    @Test
    fun testCallRepositoryRxJava() {

        Mockito.`when`(api.getLatestRatesWithRxJava(currency))
            .thenReturn(
                Single.just(newRates)
            )
        Mockito.`when`(repository.getRatesWithRxJava(currency))
            .thenReturn(
                Single.just(newRates)
            )

        rxJavaViewModel.changeCurrency(currency)
        rxJavaViewModel.startFetchingRatesAsync()

        Thread.sleep(2000)
        Mockito.verify(repository, atLeast(1)).getRatesWithRxJava(currency)

    }
}
