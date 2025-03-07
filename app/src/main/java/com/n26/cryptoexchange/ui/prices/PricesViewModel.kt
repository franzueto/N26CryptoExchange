package com.n26.cryptoexchange.ui.prices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n26.cryptoexchange.data.price.repository.PriceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PricesViewModel(
    private val repository: PriceRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    companion object {
        private const val FROM_CRYPTO_CURRENCY_SYMBOL = "BTC"
        private const val TO_FIAT_CURRENCY_CODE = "EUR"
        private const val AMOUNT_OF_PRICES_TO_FETCH = 20
        private const val POLLING_INTERVAL_IN_SECONDS = 60_000L // 1 MINUTE
    }

    private val _uiState = MutableStateFlow<PricesUiState>(PricesUiState.Loading)
    val uiState: StateFlow<PricesUiState> = _uiState.asStateFlow()

    fun loadPrices() {
        _uiState.value = PricesUiState.Loading

        viewModelScope.launch(dispatcher) {
            try {
                repository.getPriceItemsPeriodically(
                    from = FROM_CRYPTO_CURRENCY_SYMBOL,
                    to = TO_FIAT_CURRENCY_CODE,
                    amount = AMOUNT_OF_PRICES_TO_FETCH,
                    interval = POLLING_INTERVAL_IN_SECONDS
                ).collectLatest { items ->
                    _uiState.value = PricesUiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = PricesUiState.Error("Something went wrong")
            }
        }
    }
}
