package com.n26.cryptoexchange.ui.pricedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n26.cryptoexchange.data.price.repository.PriceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PriceDetailViewModel(
    private val repository: PriceRepository,
    private val enabledCurrencies: List<String> = defaultCurrencies,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    companion object {
        private const val FROM_CRYPTO_CURRENCY_SYMBOL = "BTC"
        private const val EUR_FIAT_CURRENCY_CODE = "EUR"
        private const val USD_FIAT_CURRENCY_CODE = "USD"
        private const val GBP_FIAT_CURRENCY_CODE = "GBP"

        private val defaultCurrencies = listOf(
            EUR_FIAT_CURRENCY_CODE,
            USD_FIAT_CURRENCY_CODE,
            GBP_FIAT_CURRENCY_CODE
        )
    }

    private val _uiState = MutableStateFlow<PriceDetailUiState>(PriceDetailUiState.Loading)
    val uiState: StateFlow<PriceDetailUiState> = _uiState.asStateFlow()

    fun getPriceDetail(time: Long) {
        _uiState.value = PriceDetailUiState.Loading

        viewModelScope.launch(dispatcher) {
            enabledCurrencies.forEach { currencyCode ->
                repository.getPriceItem(
                    from = FROM_CRYPTO_CURRENCY_SYMBOL,
                    to = currencyCode,
                    time
                ).collect { item ->
                    val currentValue = _uiState.value

                    if (currentValue is PriceDetailUiState.Success) {
                        _uiState.value = when (currencyCode) {
                            EUR_FIAT_CURRENCY_CODE -> currentValue.copy(eurPriceItem = item)
                            USD_FIAT_CURRENCY_CODE -> currentValue.copy(usdPriceItem = item)
                            GBP_FIAT_CURRENCY_CODE -> currentValue.copy(gbpPriceItem = item)
                            else -> PriceDetailUiState.Error("Unknown currency")
                        }
                    } else {
                        _uiState.value = when (currencyCode) {
                            EUR_FIAT_CURRENCY_CODE ->
                                PriceDetailUiState.Success(eurPriceItem = item)

                            USD_FIAT_CURRENCY_CODE ->
                                PriceDetailUiState.Success(usdPriceItem = item)

                            GBP_FIAT_CURRENCY_CODE ->
                                PriceDetailUiState.Success(gbpPriceItem = item)

                            else -> PriceDetailUiState.Error("Unknown currency")
                        }
                    }
                }
            }
        }
    }
}
