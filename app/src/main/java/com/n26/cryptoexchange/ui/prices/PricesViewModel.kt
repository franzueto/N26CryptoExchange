package com.n26.cryptoexchange.ui.prices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n26.cryptoexchange.data.price.repository.PriceRepository
import com.n26.cryptoexchange.domain.model.PriceItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PricesViewModel(
    private val repository: PriceRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    companion object {
        private const val FROM_CRYPTO_CURRENCY_SYMBOL = "BTC"
        private const val TO_FIAT_CURRENCY_CODE = "EUR"
        private const val AMOUNT_OF_PRICES_TO_FETCH = 20
    }

    private val _priceItems = MutableStateFlow<List<PriceItem>>(emptyList())
    val priceItems: StateFlow<List<PriceItem>> = _priceItems.asStateFlow()

    fun loadPrices() {
        viewModelScope.launch(dispatcher) {
            repository.getPriceItems(
                from = FROM_CRYPTO_CURRENCY_SYMBOL,
                to = TO_FIAT_CURRENCY_CODE,
                amount = AMOUNT_OF_PRICES_TO_FETCH
            ).collect { items ->
                _priceItems.value = items
            }
        }
    }
}
