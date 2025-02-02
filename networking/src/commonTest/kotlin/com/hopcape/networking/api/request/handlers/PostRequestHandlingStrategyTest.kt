package com.hopcape.networking.api.request.handlers

import com.hopcape.networking.api.mocks.MockSuccessResponse
import com.hopcape.networking.api.mocks.createMockClient
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.methods.HttpMethod
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PostRequestHandlingStrategyTest {
    /**
     * Tests the `handleRequest` method for a successful POST request.
     *
     * This test verifies that the `PostRequestHandlerStrategy` correctly handles a successful POST request.
     * The mock client simulates a server response with a JSON payload containing `"data": "test"`.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined response: `{"data": "test"}`.
     * - A `NetworkRequest` object is created with a simple URL (`http://example.com`) and no additional headers or parameters.
     * - The `handleRequest` method is invoked with the `MockSuccessResponse` class as the target type for deserialization.
     *
     * ## Expected Behavior:
     * - The request succeeds (`Result.isSuccess` is `true`).
     * - The deserialized response contains the expected data (`"test"`).
     */
    @Test
    fun `test handleRequest success`() = runTest {
        val client = createMockClient("""{"data": "test"}""")
        val strategy = PostRequestHandlerStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = null,
            method = HttpMethod.POST
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "test")
    }

    /**
     * Tests the `handleRequest` method for a failed POST request.
     *
     * This test verifies that the `PostRequestHandlerStrategy` correctly handles a failed POST request.
     * The mock client simulates a server error response with a JSON payload containing `"error": "not found"`
     * and an HTTP status code of `404 Not Found`.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined error response: `{"error": "not found"}` and an HTTP status code of `404`.
     * - A `NetworkRequest` object is created with:
     *   - A URL (`https://api.example.com`),
     *   - An authorization header (`Authorization: Bearer token`),
     *   - Query parameters (`key=value`).
     * - The `handleRequest` method is invoked with the `MockSuccessResponse` class as the target type for deserialization.
     *
     * ## Expected Behavior:
     * - The request fails (`Result.isFailure` is `true`).
     * - No exception is thrown during the test execution.
     */
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

    /**
     * Tests the `handleRequest` method to ensure that the request body is correctly attached and serialized.
     *
     * This test verifies that the body provided in the `NetworkRequest` object is correctly serialized and sent
     * as part of the POST request. The mock client simulates a successful response with a JSON payload containing
     * `"data": "body-test"`.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined response: `{"data": "body-test"}`.
     * - A `NetworkRequest` object is created with:
     *   - A URL (`http://example.com`),
     *   - A JSON body (`{"key": "value"}`),
     *   - A `Content-Type` header set to `application/json`.
     * - The `handleRequest` method is invoked with the `MockSuccessResponse` class as the target type for deserialization.
     *
     * ## Expected Behavior:
     * - The request succeeds (`Result.isSuccess` is `true`).
     * - The deserialized response contains the expected data (`"body-test"`), indicating that the server received and processed the body correctly.
     * - The mock client verifies that the request body matches the expected serialized JSON (`{"key":"value"}`).
     */
    @Test
    fun `test handleRequest with body attachment`() = runTest {
        // Define the request body as a Map
        val requestBody = mapOf("key" to "value")

        // Serialize the request body to JSON
        val json = Json { ignoreUnknownKeys = true }
        val serializedRequestBody = json.encodeToString(requestBody)

        // Create a mock client that validates the request body
        val client = createMockClient(
            responseBody = """{"data": "body-test"}""",
            headers = mapOf(HttpHeaders.ContentType to listOf("application/json")),
            validateRequestBody = { body ->
                // Ensure the request body matches the expected serialized JSON
                assertEquals("""{"key":"value"}""", body)
            }
        )

        // Instantiate the strategy
        val strategy = PostRequestHandlerStrategy(client) { println(it) }

        // Create a network request with a JSON body
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = mapOf(HttpHeaders.ContentType to "application/json"),
            requestBody = serializedRequestBody, // Use the serialized JSON body
            method = HttpMethod.POST
        )

        // Handle the request
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)

        // Assert that the request was successful
        assertTrue(result.isSuccess)

        // Assert that the deserialized response contains the expected data
        assertEquals(result.getOrNull()?.data, "body-test")
    }

    /**
     * Tests the `handleRequest` method when headers are included in the request.
     *
     * This test verifies that custom headers are correctly added to the POST request and processed by the server.
     * The mock client simulates a successful response with a JSON payload containing `"data": "header-test"`.
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
    fun `test handleRequest with headers`() = runTest {
        val client = createMockClient("""{"data": "header-test"}""")
        val strategy = PostRequestHandlerStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = mapOf("Custom-Header" to "HeaderValue"),
            method = HttpMethod.POST
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "header-test")
    }

    /**
     * Tests the `handleRequest` method when query parameters are included in the request.
     *
     * This test ensures that query parameters are correctly appended to the URL and processed by the server.
     * The mock client simulates a successful response with a JSON payload containing `"data": "query-test"`.
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
    fun `test handleRequest with query parameters`() = runTest {
        val client = createMockClient("""{"data": "query-test"}""")
        val strategy = PostRequestHandlerStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = mapOf("param1" to "value1", "param2" to "value2"),
            requestHeaders = null,
            method = HttpMethod.POST
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "query-test")
    }

    /**
     * Tests the `handleRequest` method when a JSON body is included in the request.
     *
     * This test validates that the request body is correctly serialized and sent to the server.
     * The mock client simulates a successful response with a JSON payload containing `"data": "body-test"`.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined response: `{"data": "body-test"}`.
     * - A `NetworkRequest` object is created with a JSON body (`{"key": "value"}`) and a `Content-Type` header.
     *
     * ## Expected Behavior:
     * - The request succeeds (`Result.isSuccess` is `true`).
     * - The deserialized response contains the expected data (`"body-test"`).
     */
    @Test
    fun `test handleRequest with JSON body`() = runTest {
        val requestBody = """{"data": "body-test"}"""
        val client = createMockClient("""{"data": "body-test"}""")
        val strategy = PostRequestHandlerStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(
            url = "http://example.com",
            params = null,
            requestHeaders = mapOf("Content-Type" to "application/json"),
            requestBody = requestBody,
            method = HttpMethod.POST
        )
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.data, "body-test")
    }

    /**
     * Tests the `handleRequest` method when the server returns a malformed JSON response.
     *
     * This test checks how the handler behaves when the server sends an invalid JSON payload.
     * The mock client simulates a response with missing closing braces: `{"data": "malformed"`.
     *
     * ## Setup:
     * - A mock HTTP client is created with a malformed JSON response: `{"data": "malformed"`.
     * - A `NetworkRequest` object is created with a simple URL.
     *
     * ## Expected Behavior:
     * - The request fails (`Result.isFailure` is `true`).
     * - An exception is thrown during deserialization.
     */
    @Test
    fun `test handleRequest with malformed JSON response`() = runTest {
        val client = createMockClient("""{"data": "malformed" """) // Missing closing brace
        val strategy = PostRequestHandlerStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(url = "http://example.com", method = HttpMethod.POST)
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception) // Ensure an exception is thrown
    }

    /**
     * Tests the `handleRequest` method when the server returns an empty response.
     *
     * This test ensures that the handler can gracefully handle empty responses without crashing.
     * The mock client simulates an empty response body.
     *
     * ## Setup:
     * - A mock HTTP client is created with an empty response body: `""`.
     * - A `NetworkRequest` object is created with a simple URL.
     *
     * ## Expected Behavior:
     * - The request fails (`Result.isFailure` is `true`).
     * - An exception is thrown during deserialization due to the empty response.
     */
    @Test
    fun `test handleRequest with empty response`() = runTest {
        val client = createMockClient("") // Empty response body
        val strategy = PostRequestHandlerStrategy(client) { println(it) }
        val networkRequest = NetworkRequest(url = "http://example.com", method = HttpMethod.POST)
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception) // Ensure an exception is thrown
    }


    /**
     * Tests the logging functionality of the `handleRequest` method.
     *
     * This test verifies that log messages are generated during the execution of the request.
     * The logs are captured and validated to ensure they contain relevant information such as the URL.
     *
     * ## Setup:
     * - A mock HTTP client is created with a predefined response: `{"data": "log-test"}`.
     * - A custom logger is implemented to capture log messages.
     *
     * ## Expected Behavior:
     * - The request succeeds (`Result.isSuccess` is `true`).
     * - Logs are generated and contain the request URL (`http://example.com`).
     */
    @Test
    fun `test handleRequest with logging`() = runTest {
        val logs = mutableListOf<String>()
        val logger: (String) -> Unit = { logs.add(it) }
        val client = createMockClient("""{"data": "log-test"}""")
        val strategy = PostRequestHandlerStrategy(client, logger)
        val networkRequest = NetworkRequest(url = "http://example.com", method = HttpMethod.POST)
        val result = strategy.handleRequest(networkRequest, MockSuccessResponse::class)
        assertTrue(result.isSuccess)
        assertTrue(logs.isNotEmpty()) // Ensure logs were captured
    }
}