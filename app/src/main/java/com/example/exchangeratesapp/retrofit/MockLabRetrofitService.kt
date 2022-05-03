package com.example.exchangeratesapp.retrofit

import com.example.exchangeratesapp.BuildConfig
import com.example.exchangeratesapp.Constants
import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.GetExchangeRatesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.net.URL
import java.util.concurrent.TimeUnit

object MockLabRetrofitService: IMockLabRetrofitService {
    private val mockApiService: IMockApiService

    init {
        mockApiService = createRetrofitInstance().create(IMockApiService::class.java)
    }

    private fun createRetrofitInstance(): Retrofit {
        val instagramApiUrl = URL(Constants.MOCK_LAB_URL)
        return Retrofit.Builder()
            .client(createHttpClient())
            .baseUrl(instagramApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createHttpClient(): OkHttpClient {
        lateinit var okHttpClient: OkHttpClient
        val builder: OkHttpClient.Builder = if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(logging)
        } else {
            OkHttpClient.Builder()
        }.readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        okHttpClient = builder.build()

        return okHttpClient
    }

    private interface IMockApiService {
        @GET("rates2.json")
        fun getExchangeRates(): Call<GetExchangeRatesResponse>
    }

    override fun getExchangeRates(listener: IMockLabRetrofitService.IOnGetExchangeRates) {
        val request = mockApiService.getExchangeRates()
        request.enqueue(object: Callback<GetExchangeRatesResponse> {
            override fun onResponse(
                call: Call<GetExchangeRatesResponse>,
                response: Response<GetExchangeRatesResponse>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorResponse =
                        ErrorResponse(response.errorBody().toString(), response.code())
                    listener.onFailed(errorResponse)
                }
            }

            override fun onFailure(call: Call<GetExchangeRatesResponse>, t: Throwable) {
                val errorResponse =
                    ErrorResponse(t.message.toString(), Constants.SERVER_CALL_FAILED_ERROR_CODE)
                listener.onFailed(errorResponse)
            }
        })
    }

}