package com.n26.cryptoexchange.ui.pricedetail

import com.n26.cryptoexchange.domain.model.PriceItem

sealed class PriceDetailUiState {
    object Loading : PriceDetailUiState()

    data class Success(
        val eurPriceItem: PriceItem? = null,
        val usdPriceItem: PriceItem? = null,
        val gbpPriceItem: PriceItem? = null,
    ) : PriceDetailUiState()

    data class Error(val message: String) : PriceDetailUiState()
}
