package com.n26.cryptoexchange.data.price

import com.n26.cryptoexchange.data.price.repository.PriceRepositoryImpl
import com.n26.cryptoexchange.data.remote.api.HistoricalApi
import com.n26.cryptoexchange.data.remote.models.historical.HistoricalData
import com.n26.cryptoexchange.data.remote.models.historical.HistoricalDataEntry
import com.n26.cryptoexchange.data.remote.models.historical.HistoricalResponse
import com.n26.cryptoexchange.domain.model.PriceItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PriceRepositoryImplTest {

    // Test Subject
    private lateinit var repository: PriceRepositoryImpl

    // Dependencies
    private val historicalApi: HistoricalApi = mockk()

    @Before
    fun setup() {
        repository = PriceRepositoryImpl(historicalApi)
    }

    @Test
    fun `getPriceItemsPeriodically emits correct PriceItems`() = runTest {
        // Given
        val from = "BTC"
        val to = "EUR"
        val amount = 2
        val historicalData = listOf(
            HistoricalDataEntry(1672531200L, 16500.0),
            HistoricalDataEntry(1672617600L, 16600.0)
        )
        val interval = 1000L
        val response = Response.success(
            HistoricalResponse(
                response = "success",
                data = HistoricalData(historicalData)
            )
        )
        coEvery { historicalApi.getHistoricalData(from, to, amount) } returns response

        // When
        val result = repository.getPriceItemsPeriodically(from, to, amount, interval)

        // Then
        val expected = listOf(
            PriceItem(price = 16600.0, time = 1672617600L),
            PriceItem(price = 16500.0, time = 1672531200L)
        )
        assertEquals(expected, result.first())
    }

    @Test
    fun `getPriceItemsPeriodically handles when response is not successful`() = runTest {
        // Given
        val from = "BTC"
        val to = "EUR"
        val amount = 2
        val response = Response.error<HistoricalResponse>(404, "Not Found".toResponseBody())
        coEvery { historicalApi.getHistoricalData(from, to, amount) } returns response

        // When
        repository.getPriceItemsPeriodically(from, to, amount, 1000L)

        // Then
        // Error handling should be implemented
    }

    @Test
    fun `getPriceItemsPeriodically handles when response body is null`() = runTest {
        // Given
        val from = "BTC"
        val to = "EUR"
        val amount = 2
        val response = Response.success<HistoricalResponse>(null)
        coEvery { historicalApi.getHistoricalData(from, to, amount) } returns response

        // When
        repository.getPriceItemsPeriodically(from, to, amount, 1)

        // Then
        // Error handling should be implemented
    }

    @Test
    fun `getPriceItem should return PriceItem when response is successful`() = runTest {
        // Given
        val from = "BTC"
        val to = "EUR"
        val time = 1672531200L
        val historicalData = listOf(
            HistoricalDataEntry(time = time, close = 16500.0)
        )
        val response = Response.success(
            HistoricalResponse(
                response = "success",
                data = HistoricalData(historicalData)
            )
        )
        coEvery { historicalApi.getHistoricalData(from, to, 1, time) } returns response

        // When
        val result = repository.getPriceItem(from, to, time)

        // Then
        val expected = PriceItem(price = 16500.0, time = time)
        assertEquals(expected, result.toList().last())
    }

    @Test
    fun `getPriceItem should return empty list when response is not successful`() = runTest {
        // Given
        val from = "BTC"
        val to = "EUR"
        val time = 1672531200L
        val response = Response.error<HistoricalResponse>(404, "Not Found".toResponseBody())
        coEvery { historicalApi.getHistoricalData(from, to, 1, time) } returns response

        // When
        val result = repository.getPriceItem(from, to, time).toList()

        // Then
        assertEquals(emptyList<PriceItem>(), result)
    }

    @Test
    fun `getPriceItem should return empty list when response body is null`() = runTest {
        // Given
        val from = "BTC"
        val to = "EUR"
        val time = 1672531200L
        val response = Response.success<HistoricalResponse>(null)
        coEvery { historicalApi.getHistoricalData(from, to, 1, time) } returns response

        // When
        val result = repository.getPriceItem(from, to, time).toList()

        // Then
        assertEquals(emptyList<PriceItem>(), result)
    }
}
