package com.n26.cryptoexchange.ui.prices

import com.n26.cryptoexchange.domain.model.PriceItem

sealed class PricesUiState {
    object Loading : PricesUiState()

    data class Success(val items: List<PriceItem>) : PricesUiState()

    data class Error(val message: String) : PricesUiState()
}
