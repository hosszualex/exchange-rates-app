package com.example.exchangeratesapp

import com.example.exchangeratesapp.model.ExchangePairs
import com.example.exchangeratesapp.model.ExchangeRates
import com.example.exchangeratesapp.model.GetExchangeRatesResponse

object FakeConstants {
    val mockApiExchangeRateResponse = arrayListOf(
        ExchangeRates("AUD", "USD", "1.37"),
        ExchangeRates("USD", "AUD", "0.73"),
        ExchangeRates("AUD", "EUR", "1.05"),
        ExchangeRates("EUR", "AUD", "0.95"),
        ExchangeRates("USD", "CAD", "0.51"),
        ExchangeRates("CAD", "USD", "1.96"),
        ExchangeRates("EUR", "RON", "5.00")
        )
    val mockApiExchangePairResponse = arrayListOf(
        ExchangePairs("USD", "AUD"),
        ExchangePairs("CAD", "AUD"),
        ExchangePairs("EUR", "CAD"),
        ExchangePairs("USD", "AUD"),
        ExchangePairs("AUD", "EUR"),
        ExchangePairs("EUR", "AUD"),
        ExchangePairs("AUD", "USD"),
        ExchangePairs("CAD", "USD"),
        ExchangePairs("CAD", "EUR"),
        ExchangePairs("USD", "RON")
        )

    val expectedMockResponse = listOf(
        ExchangeRates("USD", "AUD", "0.73"),
        ExchangeRates("CAD", "AUD", "1.43"),
        ExchangeRates("EUR", "CAD", "0.66"),
        ExchangeRates("USD", "AUD", "0.73"),
        ExchangeRates("AUD", "EUR", "1.05"),
        ExchangeRates("EUR", "AUD", "0.95"),
        ExchangeRates("AUD", "USD", "1.37"),
        ExchangeRates("CAD", "USD", "1.96"),
        ExchangeRates("CAD", "EUR", "1.50"),
        ExchangeRates("USD", "RON", "0.51")
    )

    val mockApiResponse = GetExchangeRatesResponse(mockApiExchangeRateResponse, mockApiExchangePairResponse)
}