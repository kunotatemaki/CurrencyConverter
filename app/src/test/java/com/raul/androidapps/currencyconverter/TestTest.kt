package com.raul.androidapps.currencyconverter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class TestTests {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var test: ClassToMock

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(test.getNumber(any())).thenReturn(9)
    }

    @Test
    fun showTitleTest() {
        runBlocking {
            val presenter = Presenter()

            val job = presenter.showTitle("title")
            job.join()

            val number = test.getNumber(0)
            assertEquals(number, 9)

            assertEquals("title", presenter.titleLiveData.value)
        }
    }
}

class ClassToMock {
    fun getNumber(value: Int) = value
}

class Presenter {

    var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob)
    private lateinit var fetchingJob: Job

    val titleLiveData = MutableLiveData<String>()

    fun showTitle(title: String): Job = viewModelScope.launch {
        titleLiveData.postValue(title)
    }.also { fetchingJob = it }


}
