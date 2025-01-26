package com.hopcape.networking.api.request.handlers

import com.hopcape.networking.api.mocks.MockSuccessResponse
import com.hopcape.networking.api.mocks.createMockClient
import com.hopcape.networking.api.mocks.mockEngine
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.methods.HttpMethod
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PostRequestHandlingStrategyTest {
    @Test
    fun `test handleRequest success`() = runTest {
        val client = createMockClient("""{"data": "test"}""")
        val strategy = PostRequestHandlerStrategy(client) { println(it) }

        val networkRequest = NetworkRequest(url = "http://example.com", params = null, requestHeaders = null, method = HttpMethod.POST)
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)

        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "test")
    }

    @Test
    fun `test handleRequest failure`() = runTest {
        val client = createMockClient("""{"error": "not found"}""", HttpStatusCode.NotFound)
        val errorStrategy = PostRequestHandlerStrategy(client) { println(it) }

        val request = NetworkRequest(
            url = "https://api.example.com",
            requestHeaders = mapOf("Authorization" to "Bearer token"),
            params = mapOf("key" to "value")
        )

        val result = errorStrategy.handleRequest(request, MockSuccessResponse::class)

        // Assert that the result is a failure
        assertTrue(result.isFailure)
    }
}