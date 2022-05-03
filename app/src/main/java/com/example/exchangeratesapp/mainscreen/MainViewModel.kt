package com.example.exchangeratesapp.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.ExchangePairs
import com.example.exchangeratesapp.model.ExchangeRates
import com.example.exchangeratesapp.repositories.IExchangeRatesRepository
import com.example.exchangeratesapp.repositories.MockLabRepositoryImpl

class MainViewModel: ViewModel() {

    private val _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private val _onError = MutableLiveData<ErrorResponse>()
    val onError: LiveData<ErrorResponse>
        get() = _onError

    private val _onGetExchangePairs = MutableLiveData<List<ExchangeRates>>()
    val onGetExchangePairs: LiveData<List<ExchangeRates>>
        get() = _onGetExchangePairs

    private val repository: IExchangeRatesRepository = MockLabRepositoryImpl()

    fun getExchangeRates() {
        if (_isBusy.value != null && _isBusy.value == true) {
            return
        }
        _isBusy.value = true
        repository.getExchangeRates(object: IExchangeRatesRepository.IOnGetExchangeRates{
            override fun onSuccess(exchangeRates: List<ExchangeRates>) {
                _isBusy.value = false
                _onGetExchangePairs.value = exchangeRates
            }

            override fun onFailed(error: ErrorResponse) {
                _isBusy.value = false
                _onError.value = error
            }

        })
    }
}