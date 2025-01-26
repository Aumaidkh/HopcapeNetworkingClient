package com.hopcape.networking.api.request.handlers

import com.hopcape.networking.api.mocks.MockSuccessResponse
import com.hopcape.networking.api.mocks.createMockClient
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.methods.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetRequestHandlingStrategyTest {

    @Test
    fun `test GET request handling strategy with valid response`() = runBlocking {
        val client = createMockClient("""{"data": "test"}""")
        val strategy = GetRequestHandlingStrategy(client) { println(it) }

        val networkRequest = NetworkRequest(url = "http://example.com", params = null, requestHeaders = null, method = HttpMethod.GET)
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)

        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "test")
    }

    @Test
    fun `test GET request handling strategy with error response`() = runBlocking {
        val client = createMockClient("""{"error": "not found"}""", HttpStatusCode.NotFound)
        val strategy = GetRequestHandlingStrategy(client) { println(it) }

        val networkRequest = NetworkRequest(url = "http://example.com", params = null, requestHeaders = null)
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)

        assertTrue(result.isFailure)
    }
}
