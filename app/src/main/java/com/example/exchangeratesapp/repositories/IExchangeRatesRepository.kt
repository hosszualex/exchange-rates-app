package com.example.exchangeratesapp.repositories

import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.ExchangePairs
import com.example.exchangeratesapp.model.ExchangeRates

interface IExchangeRatesRepository {

    fun getExchangeRates(listener: IOnGetExchangeRates)

    interface IOnGetExchangeRates{
        fun onSuccess(exchangeRates: List<ExchangeRates>, exchangePairs: List<ExchangePairs>)
        fun onFailed(error: ErrorResponse)
    }
}