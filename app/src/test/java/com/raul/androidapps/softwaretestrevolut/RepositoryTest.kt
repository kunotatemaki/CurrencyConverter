package com.raul.androidapps.softwaretestrevolut

import com.google.gson.Gson
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.JsonElement
import com.raul.androidapps.softwaretestrevolut.domain.deserializer.RatesDeserializer
import com.raul.androidapps.softwaretestrevolut.domain.model.Rates
import com.raul.androidapps.softwaretestrevolut.domain.model.SingleRate
import org.junit.Test
import com.google.gson.JsonDeserializationContext
import com.raul.androidapps.softwaretestrevolut.network.NetworkServiceFactory
import com.raul.androidapps.softwaretestrevolut.network.Resource
import com.raul.androidapps.softwaretestrevolut.network.RevolutApi
import com.raul.androidapps.softwaretestrevolut.repository.Repository
import com.raul.androidapps.softwaretestrevolut.repository.RepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.lang.reflect.Type
import java.net.ContentHandler
import java.util.*


class RepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkServiceFactory: NetworkServiceFactory

     @Mock
    lateinit var api: RevolutApi

    private lateinit var repository: Repository
    private lateinit var rates: Rates

    @Before
    fun setUp() {
        val list: List<SingleRate> = mutableListOf(
            SingleRate("EUR", 1.toBigDecimal(), true),
            SingleRate("USD", 0.90.toBigDecimal(), false),
            SingleRate("GBP", 1.12.toBigDecimal(), false)
        )
        rates = Rates(list)
        MockitoAnnotations.initMocks(this)
        repository = RepositoryImpl(networkServiceFactory)

        Mockito.`when`(networkServiceFactory.getServiceInstance())
            .thenReturn(
                api
            )
    }

    @Test
    fun testResponseSuccess() {
        runBlocking {
            val currency = "EUR"
            Mockito.`when`(api.getLatestRatesWithCoroutines(currency))
                .thenReturn(
                    Response.success(rates)
                )
            val response = repository.getRates(currency)
            assertEquals(response.status, Resource.Status.SUCCESS)
            assertEquals(response.data, rates)
        }
    }

    @Test
    fun testResponseError() {
        runBlocking {
            val currency = "EUR"
            Mockito.`when`(api.getLatestRatesWithCoroutines(currency))
                .thenReturn(
                    Response.error(404, "".toResponseBody(null))
                )
            val response = repository.getRates(currency)
            assertEquals(response.status, Resource.Status.ERROR)
            assertEquals(response.data, null)
        }
    }
}
