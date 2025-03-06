package com.n26.cryptoexchange.ui.formatter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.n26.cryptoexchange.domain.model.PriceItem
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PriceItemFormatterTest {

    @Test
    fun getFormattedPriceShouldFormatPriceWithEURCurrencyAndEURLocale() {
        // Given
        val priceItem = PriceItem(12345.67, 1672531200L)
        val currencySymbol = "€"
        val locale = Locale.GERMAN
        val expectedFormattedPrice = "€ 12.345,67"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }

    @Test
    fun getFormattedPriceShouldFormatPriceWithUSDCurrencyAndUSLocale() {
        // Given
        val priceItem = PriceItem(9876.54, 1672531200L)
        val currencySymbol = "$"
        val locale = Locale.US
        val expectedFormattedPrice = "$ 9,876.54"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }

    @Test
    fun getFormattedPriceShouldFormatPriceWithGBPCurrencyAndUKLocale() {
        // Given
        val priceItem = PriceItem(5432.10, 1672531200L)
        val currencySymbol = "£"
        val locale = Locale.UK
        val expectedFormattedPrice = "£ 5,432.10"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }

    @Test
    fun getFormattedPriceShouldFormatZeroOriceCorrectly() {
        // Given
        val priceItem = PriceItem(0.0, 1672531200L)
        val currencySymbol = "€"
        val locale = Locale.GERMAN
        val expectedFormattedPrice = "€ 0,00"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }

    @Test
    fun getFormattedPriceShouldHandleNegativePriceCorrectly() {
        // Given
        val priceItem = PriceItem(-100.50, 1672531200L)
        val currencySymbol = "$"
        val locale = Locale.US
        val expectedFormattedPrice = "$ -100.50"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }

    @Test
    fun getFormattedPriceShouldHandleLargePriceCorrectly() {
        // Given
        val priceItem = PriceItem(123456789.99, 1672531200L)
        val currencySymbol = "£"
        val locale = Locale.UK
        val expectedFormattedPrice = "£ 123,456,789.99"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }

    @Test
    fun getFormattedPriceShouldHandleDifferentDecimalPlacesCorrectly() {
        // Given
        val priceItem = PriceItem(123.4, 1672531200L)
        val currencySymbol = "€"
        val locale = Locale.GERMAN
        val expectedFormattedPrice = "€ 123,40"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }

    @Test
    fun getFormattedPriceShouldHandleDifferentCurrencySymbols() {
        // Given
        val priceItem = PriceItem(123.4, 1672531200L)
        val currencySymbol = "BTC"
        val locale = Locale.GERMAN
        val expectedFormattedPrice = "BTC 123,40"

        // When
        val formattedPrice = priceItem.getFormattedPrice(currencySymbol, locale)

        // Then
        assertEquals(expectedFormattedPrice, formattedPrice)
    }
}
