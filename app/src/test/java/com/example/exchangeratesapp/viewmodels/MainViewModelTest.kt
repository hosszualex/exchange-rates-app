package com.example.exchangeratesapp.viewmodels

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.exchangeratesapp.FakeConstants
import com.example.exchangeratesapp.mainscreen.MainViewModel
import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.repositories.MockLabRepositoryFakeImpl
import com.example.exchangeratesapp.services.MockLabFakeRetrofitService
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var fakeMockRepository: Field

    @Before
    fun setupViewModel() {
        mainViewModel = MainViewModel()
        fakeMockRepository = mainViewModel.javaClass.getDeclaredField("repository")
        fakeMockRepository.isAccessible = true
        fakeMockRepository.set(mainViewModel, MockLabRepositoryFakeImpl())
    }

    @Test
    fun onGetPostsSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        MockLabFakeRetrofitService.mockData = FakeConstants.mockApiResponse
        MockLabFakeRetrofitService.responseCode = 200

        mainViewModel.getExchangeRates()

        val value = mainViewModel.onGetExchangePairs.value
        Assert.assertEquals(FakeConstants.expectedMockResponse, value)
    }

    @Test
    fun onGetPostsFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        MockLabFakeRetrofitService.responseCode = 400

        mainViewModel.getExchangeRates()

        val error = mainViewModel.onError.value
        Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
    }
}