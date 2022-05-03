package com.example.exchangeratesapp.retrofit

import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.GetExchangeRatesResponse

interface IMockLabRetrofitService {

    fun getExchangeRates(listener: IOnGetExchangeRates)

    interface IOnGetExchangeRates {
        fun onSuccess(data: GetExchangeRatesResponse)
        fun onFailed(error: ErrorResponse)
    }
}