package com.example.exchangeratesapp.repositories

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.exchangeratesapp.FakeConstants
import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.ExchangePairs
import com.example.exchangeratesapp.model.ExchangeRates
import com.example.exchangeratesapp.services.MockLabFakeRetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class MockLabRepositoryImplTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: IExchangeRatesRepository
    private lateinit var method: Method
    private lateinit var fakeRetrofitService: Field

    @Before
    fun setupRepository() {
        mockRepository = MockLabRepositoryImpl()
        val firstCollectionType = (typeOf<ArrayList<ExchangeRates>>().classifier!! as KClass<ArrayList<ExchangeRates>>).java
        val secondCollectionType = (typeOf<ArrayList<ExchangePairs>>().classifier!! as KClass<ArrayList<ExchangePairs>>).java

        method = mockRepository.javaClass.getDeclaredMethod("getCompleteRateExchangeDataSet", firstCollectionType, secondCollectionType)
        method.isAccessible = true

        fakeRetrofitService = mockRepository.javaClass.getDeclaredField("retrofitService")
        fakeRetrofitService.isAccessible = true
        fakeRetrofitService.set(mockRepository, MockLabFakeRetrofitService)
    }

    @Test
    fun getOrdersFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(mockRepository, FakeConstants.mockApiExchangeRateResponse, FakeConstants.mockApiExchangePairResponse)
        Assert.assertEquals(FakeConstants.expectedMockResponse, value)
    }

    @Test
    fun getEmptyOrdersFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(mockRepository, arrayListOf<ExchangeRates>(), arrayListOf<ExchangePairs>())
        Assert.assertEquals(true, (value as List<*>).isEmpty())
    }

    @Test
    fun onGetRepositoryOrdersSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        MockLabFakeRetrofitService.mockData = FakeConstants.mockApiResponse
        MockLabFakeRetrofitService.responseCode = 200

        runBlocking(Dispatchers.IO) {
            mockRepository.getExchangeRates(object: IExchangeRatesRepository.IOnGetExchangeRates{
                override fun onSuccess(exchangeRates: List<ExchangeRates>) {
                    Assert.assertEquals(FakeConstants.expectedMockResponse, exchangeRates)
                }

                override fun onFailed(error: ErrorResponse) {}
            })
        }
    }

    @Test
    fun onGetRepositoryOrdersFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        MockLabFakeRetrofitService.responseCode = 400
        runBlocking(Dispatchers.IO) {
            mockRepository.getExchangeRates(object: IExchangeRatesRepository.IOnGetExchangeRates{
                override fun onSuccess(exchangeRates: List<ExchangeRates>) {
                }

                override fun onFailed(error: ErrorResponse) {
                    Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
                }
            })
        }
    }
}