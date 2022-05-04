package com.example.exchangeratesapp.services

import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.GetExchangeRatesResponse
import com.example.exchangeratesapp.retrofit.IMockLabRetrofitService

object MockLabFakeRetrofitService : IMockLabRetrofitService {

    var mockData: GetExchangeRatesResponse? = null
    var responseCode: Int = 200


    override fun getExchangeRates(listener: IMockLabRetrofitService.IOnGetExchangeRates) {
        when (responseCode) {
            200 -> {
                if (mockData != null) {
                    listener.onSuccess(mockData!!)
                }
            }
            400 -> {
                listener.onFailed(ErrorResponse("Server is unreachable", 400))
            }
        }
    }
}