package com.n26.cryptoexchange.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.n26.cryptoexchange.data.price.repository.PriceRepositoryImpl
import com.n26.cryptoexchange.data.remote.RetrofitClient
import com.n26.cryptoexchange.ui.pricedetail.PriceDetailViewModel
import com.n26.cryptoexchange.ui.prices.PricesViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PricesViewModel::class.java) -> PricesViewModel(
                PriceRepositoryImpl(RetrofitClient.historicalApiService)
            ) as T
            modelClass.isAssignableFrom(PriceDetailViewModel::class.java) -> PriceDetailViewModel(
                PriceRepositoryImpl(RetrofitClient.historicalApiService)
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
