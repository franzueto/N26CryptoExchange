package com.n26.cryptoexchange.di

import androidx.lifecycle.ViewModel
import com.n26.cryptoexchange.ui.pricedetail.PriceDetailViewModel
import com.n26.cryptoexchange.ui.prices.PricesViewModel
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ViewModelFactoryTest {

    // Test Subject
    private lateinit var viewModelFactory: ViewModelFactory

    @Before
    fun setup() {
        viewModelFactory = ViewModelFactory()
    }

    @Test
    fun `create should return PricesViewModel`() {
        // Given
        val modelClass = PricesViewModel::class.java

        // When
        val viewModel = viewModelFactory.create(modelClass)

        // Then
        assertTrue(viewModel is PricesViewModel)
    }

    @Test
    fun `create should return PriceDetailViewModel`() {
        // Given
        val modelClass = PriceDetailViewModel::class.java

        // When
        val viewModel = viewModelFactory.create(modelClass)

        // Then
        assertTrue(viewModel is PriceDetailViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create should throw IllegalArgumentException for unknown ViewModel`() {
        // Given
        val model = object : ViewModel() {}
        val modelClass = model::class.java

        // When
        viewModelFactory.create(modelClass)

        // Then
        // Expecting IllegalArgumentException
    }
}
