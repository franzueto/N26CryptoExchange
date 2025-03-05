package com.n26.cryptoexchange.ui.prices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n26.cryptoexchange.data.price.repository.PriceRepository
import com.n26.cryptoexchange.domain.model.PriceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PricesViewModel(
    private val repository: PriceRepository
) : ViewModel() {

    private val _priceItems = MutableStateFlow<List<PriceItem>>(emptyList())
    val priceItems: StateFlow<List<PriceItem>> = _priceItems.asStateFlow()

    fun loadPrices() {
        viewModelScope.launch {
            repository.getPriceItems(
                from = "BTC",
                to = "EUR",
                amount = 20
            ).collect { items ->
                _priceItems.value = items
            }
        }
    }
}
