package com.example.exchangeratesapp

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

    private val repository: IExchangeRatesRepository = MockLabRepositoryImpl()



    fun getExchangeRates() {
        repository.getExchangeRates(object: IExchangeRatesRepository.IOnGetExchangeRates{
            override fun onSuccess(
                exchangeRates: List<ExchangeRates>) {
                var asd = 0;
            }

            override fun onFailed(error: ErrorResponse) {
                var asd = 0;
            }

        })
    }
}