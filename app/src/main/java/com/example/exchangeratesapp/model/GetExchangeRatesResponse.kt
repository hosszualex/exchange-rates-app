package com.example.exchangeratesapp.model

data class GetExchangeRatesResponse(
    val rates: ArrayList<ExchangeRates>,
    val pairs: ArrayList<ExchangePairs>
)