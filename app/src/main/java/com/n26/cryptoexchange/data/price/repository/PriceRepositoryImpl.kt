package com.n26.cryptoexchange.data.price.repository

import com.n26.cryptoexchange.domain.model.PriceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PriceRepositoryImpl : PriceRepository {

    override fun getPriceItems(): Flow<List<PriceItem>> = flow {
        val items = List(15) { index ->
            PriceItem(
                price = "$${(index + 1) * 10}.00",
                date = "2025-02-${index + 1}"
            )
        }
        emit(items)
    }
}
