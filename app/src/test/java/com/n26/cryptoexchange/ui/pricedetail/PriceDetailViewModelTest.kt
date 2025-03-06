package com.n26.cryptoexchange.ui.pricedetail

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

class PriceDetailViewModelTest {

    // Test Subject
    private lateinit var viewModel: PriceDetailViewModel

    // Dependencies
    private val repository: PriceRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        viewModel = PriceDetailViewModel(
            repository = repository,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `getPriceDetail update uiState with success when repository returns data`() = runTest {
        // Given
        val eurPriceItem = PriceItem(time = 1672531200L, price = 16500.0)
        val usdPriceItem = PriceItem(time = 1672531200L, price = 17000.0)
        val gbpPriceItem = PriceItem(time = 1672531200L, price = 15000.0)
        val time = 1672531200L

        coEvery { repository.getPriceItem("BTC", "EUR", time) } returns flowOf(eurPriceItem)
        coEvery { repository.getPriceItem("BTC", "USD", time) } returns flowOf(usdPriceItem)
        coEvery { repository.getPriceItem("BTC", "GBP", time) } returns flowOf(gbpPriceItem)

        // When
        viewModel.getPriceDetail(time)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = PriceDetailUiState.Success(
            eurPriceItem = eurPriceItem,
            usdPriceItem = usdPriceItem,
            gbpPriceItem = gbpPriceItem
        )
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `getPriceDetail should update uiState with loading initially`() = runTest {
        // Given
        val time = 1672531200L

        coEvery {
            repository.getPriceItem("BTC", any(), time)
        } returns flowOf(PriceItem(0.0, 0L))

        // When
        viewModel.getPriceDetail(time)

        // Then
        val actualUiState = viewModel.uiState.value
        assertEquals(PriceDetailUiState.Loading, actualUiState)
    }

    @Test
    fun `getPriceDetail should update uiState correctly when called multiple times`() = runTest {
        // Given
        val time1 = 1672531200L
        val time2 = 1672617600L

        val eurPriceItem1 = PriceItem(time = time1, price = 16500.0)
        val usdPriceItem1 = PriceItem(time = time1, price = 17000.0)
        val gbpPriceItem1 = PriceItem(time = time1, price = 15000.0)

        val eurPriceItem2 = PriceItem(time = time2, price = 16600.0)
        val usdPriceItem2 = PriceItem(time = time2, price = 17100.0)
        val gbpPriceItem2 = PriceItem(time = time2, price = 15100.0)

        coEvery { repository.getPriceItem("BTC", "EUR", time1) } returns flowOf(eurPriceItem1)
        coEvery { repository.getPriceItem("BTC", "USD", time1) } returns flowOf(usdPriceItem1)
        coEvery { repository.getPriceItem("BTC", "GBP", time1) } returns flowOf(gbpPriceItem1)

        coEvery { repository.getPriceItem("BTC", "EUR", time2) } returns flowOf(eurPriceItem2)
        coEvery { repository.getPriceItem("BTC", "USD", time2) } returns flowOf(usdPriceItem2)
        coEvery { repository.getPriceItem("BTC", "GBP", time2) } returns flowOf(gbpPriceItem2)

        // When
        viewModel.getPriceDetail(time1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.getPriceDetail(time2)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val actualUiState = viewModel.uiState.value
        val expectedUiState = PriceDetailUiState.Success(
            eurPriceItem = eurPriceItem2,
            usdPriceItem = usdPriceItem2,
            gbpPriceItem = gbpPriceItem2
        )
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `getPriceDetail should update uiState when repository USD first`() = runTest {
        // Given
        val eurPriceItem = PriceItem(time = 1672531200L, price = 16500.0)
        val usdPriceItem = PriceItem(time = 1672531200L, price = 17000.0)
        val gbpPriceItem = PriceItem(time = 1672531200L, price = 15000.0)
        val time = 1672531200L

        coEvery { repository.getPriceItem("BTC", "EUR", time) } returns flowOf(eurPriceItem)
        coEvery { repository.getPriceItem("BTC", "USD", time) } returns flowOf(usdPriceItem)
        coEvery { repository.getPriceItem("BTC", "GBP", time) } returns flowOf(gbpPriceItem)

        val viewModel = PriceDetailViewModel(
            repository,
            listOf("USD", "EUR", "GBP"),
            testDispatcher
        )

        // When
        viewModel.getPriceDetail(time)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = PriceDetailUiState.Success(
            eurPriceItem = eurPriceItem,
            usdPriceItem = usdPriceItem,
            gbpPriceItem = gbpPriceItem
        )
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `getPriceDetail should update uiState when repository GBP first`() = runTest {
        // Given
        val eurPriceItem = PriceItem(time = 1672531200L, price = 16500.0)
        val usdPriceItem = PriceItem(time = 1672531200L, price = 17000.0)
        val gbpPriceItem = PriceItem(time = 1672531200L, price = 15000.0)
        val time = 1672531200L

        coEvery {
            repository.getPriceItem("BTC", "EUR", time)
        } returns flowOf(eurPriceItem)
        coEvery {
            repository.getPriceItem("BTC", "USD", time)
        } returns flowOf(usdPriceItem)
        coEvery {
            repository.getPriceItem("BTC", "GBP", time)
        } returns flowOf(gbpPriceItem)

        val viewModel = PriceDetailViewModel(
            repository,
            listOf("GBP", "EUR", "USD"),
            testDispatcher
        )

        // When
        viewModel.getPriceDetail(time)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = PriceDetailUiState.Success(
            eurPriceItem = eurPriceItem,
            usdPriceItem = usdPriceItem,
            gbpPriceItem = gbpPriceItem
        )
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `getPriceDetail update uiState with error when repository returns unknown`() = runTest {
        // Given
        val eurPriceItem = PriceItem(time = 1672531200L, price = 16500.0)
        val time = 1672531200L

        coEvery { repository.getPriceItem("BTC", "unknown", time) } returns flowOf(eurPriceItem)

        val viewModel = PriceDetailViewModel(repository, listOf("unknown"), testDispatcher)

        // When
        viewModel.getPriceDetail(time)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = PriceDetailUiState.Error("Unknown currency")
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `getPriceDetail with error if repository returns unknown a second time`() = runTest {
        // Given
        val eurPriceItem = PriceItem(time = 1672531200L, price = 16500.0)
        val usdPriceItem = PriceItem(time = 1672531200L, price = 17000.0)
        val gbpPriceItem = PriceItem(time = 1672531200L, price = 15000.0)
        val time = 1672531200L

        coEvery {
            repository.getPriceItem("BTC", "EUR", time)
        } returns flowOf(eurPriceItem)
        coEvery {
            repository.getPriceItem("BTC", "USD", time)
        } returns flowOf(usdPriceItem)
        coEvery {
            repository.getPriceItem("BTC", "unknown", time)
        } returns flowOf(gbpPriceItem)

        val viewModel = PriceDetailViewModel(
            repository,
            listOf("EUR", "USD", "unknown"),
            testDispatcher
        )

        // When
        viewModel.getPriceDetail(time)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = PriceDetailUiState.Error("Unknown currency")
        assertEquals(expectedUiState, actualUiState)
    }
}
