package com.n26.cryptoexchange.ui.prices

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.n26.cryptoexchange.domain.model.PriceItem
import com.n26.cryptoexchange.ui.formatter.getFormattedPrice
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PricesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: PricesViewModel

    @Before
    fun setup() {
        mockViewModel = mockk(relaxed = true)
    }

    @Test
    fun pricesScreen_displaysPrices_whenSuccessState() {
        // Given
        val priceItems = listOf(
            PriceItem(price = 100.0, time = 1L),
            PriceItem(price = 200.0, time = 2L),
            PriceItem(price = 300.0, time = 3L)
        )
        val successState = PricesUiState.Success(priceItems)
        every { mockViewModel.uiState } returns MutableStateFlow(successState)

        // When
        composeTestRule.setContent {
            PricesScreen(
                onItemClick = {},
                viewModel = mockViewModel
            )
        }

        // Then
        priceItems.forEach { item ->
            composeTestRule.onNodeWithText(
                item.getFormattedPrice("€")
            ).assertIsDisplayed()
        }
    }
}
