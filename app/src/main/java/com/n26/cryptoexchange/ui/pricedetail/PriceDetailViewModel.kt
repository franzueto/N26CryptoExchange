package com.n26.cryptoexchange.ui.pricedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n26.cryptoexchange.data.price.repository.PriceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PriceDetailViewModel(
    private val repository: PriceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PriceDetailUiState>(PriceDetailUiState.Loading)
    val uiState: StateFlow<PriceDetailUiState> = _uiState.asStateFlow()

    fun getPriceDetail(time: Long) {
        _uiState.value = PriceDetailUiState.Loading

        viewModelScope.launch {
            repository.getPriceItem(
                from = "BTC",
                to = "EUR",
                time
            ).collect { item ->
                val currentValue = _uiState.value

                if (currentValue is PriceDetailUiState.Success) {
                    _uiState.value = currentValue.copy(
                        eurPriceItem = item
                    )
                } else {
                    _uiState.value = PriceDetailUiState.Success(
                        eurPriceItem = item
                    )
                }
            }

            repository.getPriceItem(
                from = "BTC",
                to = "EUR",
                time
            ).collect { item ->
                val currentValue = _uiState.value

                if (currentValue is PriceDetailUiState.Success) {
                    _uiState.value = currentValue.copy(
                        eurPriceItem = item
                    )
                } else {
                    _uiState.value = PriceDetailUiState.Success(
                        eurPriceItem = item
                    )
                }
            }

            repository.getPriceItem(
                from = "BTC",
                to = "USD",
                time
            ).collect { item ->
                val currentValue = _uiState.value

                if (currentValue is PriceDetailUiState.Success) {
                    _uiState.value = currentValue.copy(
                        usdPriceItem = item
                    )
                } else {
                    _uiState.value = PriceDetailUiState.Success(
                        usdPriceItem = item
                    )
                }
            }

            repository.getPriceItem(
                from = "BTC",
                to = "GBP",
                time
            ).collect { item ->
                val currentValue = _uiState.value

                if (currentValue is PriceDetailUiState.Success) {
                    _uiState.value = currentValue.copy(
                        gbpPriceItem = item
                    )
                } else {
                    _uiState.value = PriceDetailUiState.Success(
                        gbpPriceItem = item
                    )
                }
            }
        }
    }
}
