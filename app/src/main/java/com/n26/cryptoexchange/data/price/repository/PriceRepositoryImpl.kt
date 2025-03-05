package com.n26.cryptoexchange.data.price.repository

import com.n26.cryptoexchange.data.remote.api.HistoricalApi
import com.n26.cryptoexchange.domain.model.PriceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PriceRepositoryImpl(
    private val historicalApi: HistoricalApi
) : PriceRepository {

    override suspend fun getPriceItems(
        from: String,
        to: String,
        amount: Int
    ): Flow<List<PriceItem>> = flow {
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
