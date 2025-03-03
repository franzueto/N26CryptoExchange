package com.n26.cryptoexchange.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.n26.cryptoexchange.data.price.repository.PriceRepositoryImpl
import com.n26.cryptoexchange.ui.prices.PricesViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PricesViewModel::class.java) -> PricesViewModel(
                PriceRepositoryImpl()
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
