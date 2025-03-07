package com.n26.cryptoexchange.data.price.repository

import com.n26.cryptoexchange.data.remote.api.HistoricalApi
import com.n26.cryptoexchange.domain.model.PriceItem
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PriceRepositoryImpl(
    private val historicalApi: HistoricalApi
) : PriceRepository {

    override suspend fun getPriceItemsPeriodically(
        from: String,
        to: String,
        amount: Int,
        interval: Long
    ): Flow<List<PriceItem>> = flow {
        val tickerFlow = ticker(
            delayMillis = interval,
            initialDelayMillis = 0
        )

        for (tick in tickerFlow) {
            val response = historicalApi.getHistoricalData(
                fromSymbol = from,
                toSymbol = to,
                limit = amount
            )

            val data = response.body()?.data?.data
            if (response.isSuccessful && data != null) {
                data.map {
                    PriceItem(
                        price = it.close,
                        time = it.time
                    )
                }.let { emit(it.reversed()) }
            }
        }
    }

    override suspend fun getPriceItem(
        from: String,
        to: String,
        time: Long
    ): Flow<PriceItem> = flow {
        val response = historicalApi.getHistoricalData(
            fromSymbol = from,
            toSymbol = to,
            limit = 1,
            toTs = time
        )

        val data = response.body()?.data?.data
        if (response.isSuccessful && data != null) {
            data.map {
                PriceItem(
                    price = it.close,
                    time = it.time
                )
            }.let { emit(it.last()) }
        }
    }
}
