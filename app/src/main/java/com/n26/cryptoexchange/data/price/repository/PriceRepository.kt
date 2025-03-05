package com.n26.cryptoexchange.data.price.repository

import com.n26.cryptoexchange.domain.model.PriceItem
import kotlinx.coroutines.flow.Flow

interface PriceRepository {

    suspend fun getPriceItems(
        from: String,
        to: String,
        amount: Int,
    ): Flow<List<PriceItem>>
}
