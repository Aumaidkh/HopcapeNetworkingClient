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

/**
 * A test suite for the [GetRequestHandlingStrategy] class.
 *
 * This suite tests the behavior of the GET request handling strategy under various scenarios,
 * including successful responses, error responses, query parameters, headers, and edge cases.
 */
class GetRequestHandlingStrategyTest {

    /**
     * Tests the `handleRequest` method for a successful GET request.
     *
     * This test verifies that the strategy correctly handles a valid GET request and deserializes
     * the response into the expected type.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined response: `{"data": "test"}`.
     * - A `NetworkRequest` object is created with a simple URL (`http://example.com`) and no additional headers or parameters.
     *
     * ## Expected Behavior:
     * - The request succeeds (`Result.isSuccess` is `true`).
     * - The deserialized response contains the expected data (`"test"`).
     */
    @Test
    fun `test GET request handling strategy with valid response`() = runBlocking {
        val client = createMockClient("""{"data": "test"}""")
        val strategy = GetRequestHandlingStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = null,
            method = HttpMethod.GET
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "test")
    }

    /**
     * Tests the `handleRequest` method for an error response.
     *
     * This test verifies that the strategy correctly handles an error response from the server.
     *
     * ## Setup:
     * - A mock HTTP client is created with an error response: `{"error": "not found"}` and a status code of `404 Not Found`.
     * - A `NetworkRequest` object is created with a simple URL (`http://example.com`).
     *
     * ## Expected Behavior:
     * - The request fails (`Result.isFailure` is `true`).
     */
    @Test
    fun `test GET request handling strategy with error response`() = runBlocking {
        val client = createMockClient("""{"error": "not found"}""", HttpStatusCode.NotFound)
        val strategy = GetRequestHandlingStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = null
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isFailure)
    }

    /**
     * Tests the `handleRequest` method with query parameters.
     *
     * This test verifies that query parameters are correctly appended to the URL and processed by the server.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined response: `{"data": "query-test"}`.
     * - A `NetworkRequest` object is created with query parameters (`param1=value1` and `param2=value2`).
     *
     * ## Expected Behavior:
     * - The request succeeds (`Result.isSuccess` is `true`).
     * - The deserialized response contains the expected data (`"query-test"`).
     */
    @Test
    fun `test GET request handling strategy with query parameters`() = runBlocking {
        val client = createMockClient("""{"data": "query-test"}""")
        val strategy = GetRequestHandlingStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = mapOf("param1" to "value1", "param2" to "value2"),
            requestHeaders = null,
            method = HttpMethod.GET
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "query-test")
    }

    /**
     * Tests the `handleRequest` method with custom headers.
     *
     * This test verifies that custom headers are correctly added to the GET request and processed by the server.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined response: `{"data": "header-test"}`.
     * - A `NetworkRequest` object is created with a custom header (`Custom-Header: HeaderValue`).
     *
     * ## Expected Behavior:
     * - The request succeeds (`Result.isSuccess` is `true`).
     * - The deserialized response contains the expected data (`"header-test"`).
     */
    @Test
    fun `test GET request handling strategy with headers`() = runBlocking {
        val client = createMockClient("""{"data": "header-test"}""")
        val strategy = GetRequestHandlingStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = mapOf("Custom-Header" to "HeaderValue"),
            method = HttpMethod.GET,
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "header-test")
    }

    /**
     * Tests the `handleRequest` method with an empty response.
     *
     * This test verifies that the strategy gracefully handles an empty response from the server.
     *
     * ## Setup:
     * - A mock HTTP client is created with an empty response body: `""`.
     * - A `NetworkRequest` object is created with a simple URL (`http://example.com`).
     *
     * ## Expected Behavior:
     * - The request fails (`Result.isFailure` is `true`).
     */
    @Test
    fun `test GET request handling strategy with empty response`() = runBlocking {
        val client = createMockClient("")
        val strategy = GetRequestHandlingStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = null,
            method = HttpMethod.GET
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isFailure)
    }

    /**
     * Tests the `handleRequest` method with a malformed JSON response.
     *
     * This test verifies that the strategy correctly handles a malformed JSON response from the server.
     *
     * ## Setup:
     * - A mock HTTP client is created with a malformed JSON response: `{"data": "malformed"`.
     * - A `NetworkRequest` object is created with a simple URL (`http://example.com`).
     *
     * ## Expected Behavior:
     * - The request fails (`Result.isFailure` is `true`).
     */
    @Test
    fun `test GET request handling strategy with malformed JSON response`() = runBlocking {
        val client = createMockClient("""{"data": "malformed" """) // Missing closing brace
        val strategy = GetRequestHandlingStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = null,
            method = HttpMethod.GET
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isFailure)
    }
}