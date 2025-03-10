package com.n26.cryptoexchange.data.remote

import com.n26.cryptoexchange.data.remote.api.HistoricalApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://min-api.cryptocompare.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val historicalApiService: HistoricalApi by lazy {
        retrofit.create(HistoricalApi::class.java)
    }
}
