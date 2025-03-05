package com.n26.cryptoexchange.data.remote.api

import com.n26.cryptoexchange.data.remote.models.historical.HistoricalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoricalApi {

    @GET("/data/v2/histoday")
    suspend fun getHistoricalData(
        @Query("fsym") fromSymbol: String,
        @Query("tsym") toSymbol: String,
        @Query("limit") limit: Int
    ): Response<HistoricalResponse>
}
