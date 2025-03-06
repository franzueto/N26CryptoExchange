package com.n26.cryptoexchange.data.remote

import java.net.HttpURLConnection
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class RetrofitClientTest {

    // Collaborator
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `historicalApiService should not be null`() {
        // Given

        // When
        val service = RetrofitClient.historicalApiService

        // Then
        assertNotNull(service)
    }

    @Test
    fun `historicalApiService should return a valid response`() = runTest {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{}")
        mockWebServer.enqueue(mockResponse)
        val service = RetrofitClient.historicalApiService

        // When
        val response = service.getHistoricalData("BTC", "EUR", 10)

        // Then
        assert(response.isSuccessful)
    }
}
