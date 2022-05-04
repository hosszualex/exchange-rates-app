package com.example.exchangeratesapp.repositories

import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.ExchangeRates

interface IExchangeRatesRepository {

    fun getExchangeRates(listener: IOnGetExchangeRates)

    interface IOnGetExchangeRates{
        fun onSuccess(exchangeRates: List<ExchangeRates>)
        fun onFailed(error: ErrorResponse)
    }
}