package com.example.exchangeratesapp.repositories

import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.ExchangePairs
import com.example.exchangeratesapp.model.ExchangeRates
import com.example.exchangeratesapp.model.GetExchangeRatesResponse
import com.example.exchangeratesapp.retrofit.IMockLabRetrofitService
import com.example.exchangeratesapp.retrofit.MockLabRetrofitService

class MockLabRepositoryImpl : IExchangeRatesRepository {
    private val retrofitService: IMockLabRetrofitService

    init {
        retrofitService = MockLabRetrofitService
    }

    override fun getExchangeRates(listener: IExchangeRatesRepository.IOnGetExchangeRates) {
        retrofitService.getExchangeRates(object : IMockLabRetrofitService.IOnGetExchangeRates {
            override fun onSuccess(data: GetExchangeRatesResponse) {
                var result = getRatesForNonOffered(data.rates, data.pairs)
                listener.onSuccess(data.rates, data.pairs)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    private fun getRatesForNonOffered(
        rates: ArrayList<ExchangeRates>,
        pairs: ArrayList<ExchangePairs>
    ): List<ExchangeRates> {
        val finalExchangeRates = mutableListOf<ExchangeRates>()

        pairs.forEach { pair ->
            var rateNumber = 0f
            val start = pair.from
            val end = pair.to
            var iteration = pair.from

            run runBlock@{
                rates.forEach { startRate ->
                    if (startRate.from == start) {
                        rateNumber = startRate.rate.toFloat()
                        if (startRate.to == end) {
                            return@runBlock
                        } else {

                            val connectors = mutableListOf<String>(startRate.from, startRate.to)
                            rates.forEach { connectorRate ->
                                if (connectors.last() == end) {
                                    return@runBlock
                                }
                                val isConnector = connectors.last() == connectorRate.from
                                if (isConnector && connectors.find { it == connectorRate.to } == null) {
                                    connectors.add(connectorRate.to)
                                    rateNumber *= connectorRate.rate.toFloat()
                                }
                            }
                        }
                    }
                }
            }

            finalExchangeRates.add(ExchangeRates(start, end, rateNumber.toString()))
        }

        return finalExchangeRates.toList()
    }

}