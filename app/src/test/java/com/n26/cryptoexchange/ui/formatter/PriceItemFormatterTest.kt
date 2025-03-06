package com.n26.cryptoexchange.ui.formatter

import com.n26.cryptoexchange.domain.model.PriceItem
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class PriceItemFormatterTest {

    @Test
    fun `getFormattedDate should format not be null`() {
        // Given
        val timestamp: Long = 1672531200

        // When
        val formattedDate = timestamp.getFormattedDate()

        // Then
        assertNotNull(formattedDate)
    }

    @Test
    fun `getFormattedDate should format date correctly`() {
        // Given
        val timestamp: Long = 1672531200
        val expectedFormattedDate = "Jan 01, 2023"

        // When
        val formattedDate = timestamp.getFormattedDate(ZoneId.of("GMT"))

        // Then
        assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test
    fun `getFormattedDate should handle different timestamps`() {
        // Given
        val timestamp: Long = 1698796800
        val expectedFormattedDate = "Nov 01, 2023"

        // When
        val formattedDate = timestamp.getFormattedDate(ZoneId.of("GMT"))

        // Then
        assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test
    fun `getFormattedDate should handle leap year`() {
        // Given
        val timestamp: Long = 1709193600 // February 29, 2024, 00:00:00 UTC (leap year)
        val expectedFormattedDate = "Feb 29, 2024"

        // When
        val formattedDate = timestamp.getFormattedDate(ZoneId.of("UTC"))

        // Then
        assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test
    fun `PriceItem getFormattedDate should format date correctly`() {
        // Given
        val timestamp: Long = 1672531200
        val expectedFormattedDate = "Jan 01, 2023"

        // When
        val formattedDate = PriceItem(0.0, timestamp)
            .getFormattedDate(ZoneId.of("GMT"))

        // Then
        assertEquals(expectedFormattedDate, formattedDate)
    }
}
