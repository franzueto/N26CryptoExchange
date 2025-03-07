package com.n26.cryptoexchange.ui.prices

import com.n26.cryptoexchange.data.price.repository.PriceRepository
import com.n26.cryptoexchange.domain.model.PriceItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PricesViewModelTest {

    // Test Subject
    private lateinit var viewModel: PricesViewModel

    // Dependencies
    private val repository: PriceRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        viewModel = PricesViewModel(repository, testDispatcher)
    }

    @Test
    fun `loadPrices should update priceItems with data from repository`() = runTest {
        // Given
        val expectedPriceItems = listOf(
            PriceItem(price = 16500.0, time = 1672531200L),
            PriceItem(price = 16600.0, time = 1672617600L)
        )
        coEvery {
            repository.getPriceItemsPeriodically("BTC", "EUR", 20, 60_000L)
        } returns flowOf(expectedPriceItems)

        // When
        viewModel.loadPrices()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        assertEquals(expectedPriceItems, (actualUiState as PricesUiState.Success).items)
    }

    @Test
    fun `loadPrices should handle empty list from repository`() = runTest {
        // Given
        val expectedPriceItems = emptyList<PriceItem>()
        coEvery {
            repository.getPriceItemsPeriodically("BTC", "EUR", 20, 60_000L)
        } returns flowOf(expectedPriceItems)

        // When
        viewModel.loadPrices()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        assertEquals(expectedPriceItems, (actualUiState as PricesUiState.Success).items)
    }

    @Test
    fun `loadPrices should handle exception`() = runTest {
        // Given
        coEvery {
            repository.getPriceItemsPeriodically("BTC", "EUR", 20, 60_000L)
        } throws IllegalStateException()

        // When
        viewModel.loadPrices()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        assertEquals("Something went wrong", (actualUiState as PricesUiState.Error).message)
    }
}
