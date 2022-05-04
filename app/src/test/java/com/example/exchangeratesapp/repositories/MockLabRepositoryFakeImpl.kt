package com.example.exchangeratesapp.repositories

import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.ExchangePairs
import com.example.exchangeratesapp.model.ExchangeRates
import com.example.exchangeratesapp.model.GetExchangeRatesResponse
import com.example.exchangeratesapp.retrofit.IMockLabRetrofitService
import com.example.exchangeratesapp.services.MockLabFakeRetrofitService

class MockLabRepositoryFakeImpl : IExchangeRatesRepository {
    private val retrofitService: IMockLabRetrofitService

    init {
        retrofitService = MockLabFakeRetrofitService
    }

    override fun getExchangeRates(listener: IExchangeRatesRepository.IOnGetExchangeRates) {
        retrofitService.getExchangeRates(object : IMockLabRetrofitService.IOnGetExchangeRates {
            override fun onSuccess(data: GetExchangeRatesResponse) {
                val result = getCompleteRateExchangeDataSet(data.rates, data.pairs)
                listener.onSuccess(result)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    private fun getCompleteRateExchangeDataSet(
        rates: ArrayList<ExchangeRates>,
        pairs: ArrayList<ExchangePairs>
    ): List<ExchangeRates> {
        val finalExchangeRates = mutableListOf<ExchangeRates>()
        pairs.forEach { pair ->
            val start = pair.from
            val end = pair.to
            val rateNumber = calculateExchangeRate(rates, start, end)

            finalExchangeRates.add(ExchangeRates(start, end, String.format("%.2f", rateNumber)))
        }

        return finalExchangeRates.toList()
    }

    private fun calculateExchangeRate(
        rates: ArrayList<ExchangeRates>,
        start: String,
        end: String
    ): Float {
        var rateNumber = 0f
        run runBlock@{
            rates.forEach { startRate ->
                if (startRate.from == start) {
                    rateNumber = startRate.rate.toFloat()
                    val connectors = mutableListOf(startRate.from, startRate.to)
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
        return rateNumber
    }

}