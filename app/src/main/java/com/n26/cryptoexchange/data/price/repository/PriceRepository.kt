package com.n26.cryptoexchange.data.price.repository

import com.n26.cryptoexchange.domain.model.PriceItem
import kotlinx.coroutines.flow.Flow

interface PriceRepository {

    suspend fun getPriceItemsPeriodically(
        from: String,
        to: String,
        amount: Int,
        interval: Long,
    ): Flow<List<PriceItem>>

    suspend fun getPriceItem(
        from: String,
        to: String,
        time: Long,
    ): Flow<PriceItem>
}
