package com.example.exchangeratesapp

import com.example.exchangeratesapp.model.ExchangePairs
import com.example.exchangeratesapp.model.ExchangeRates
import com.example.exchangeratesapp.model.GetExchangeRatesResponse

object Constants {
    //=====API CONSTANTS=====//
    const val MOCK_LAB_URL = "http://gnb.dev.airtouchmedia.com/"

    //=====SHARED PREFERENCES CONSTANTS=====//
    const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
    const val PREFERENCES_LANGUAGE_KEY = "PREFERENCES_LANGUAGE_KEY"

    //=====ERROR CODES=====//
    const val SERVER_CALL_FAILED_ERROR_CODE = 400


    val mock = GetExchangeRatesResponse(arrayListOf<ExchangeRates>(),
        arrayListOf<ExchangePairs>())
}