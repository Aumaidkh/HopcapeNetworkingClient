package com.hopcape.networking.api.client

import com.hopcape.networking.api.Url
import com.hopcape.networking.api.mocks.MockSuccessResponse
import com.hopcape.networking.api.mocks.createMockClient
import com.hopcape.networking.api.mocks.mockConfiguration
import com.hopcape.networking.api.request.RequestBuilder
import com.hopcape.networking.api.request.methods.HttpMethod
import com.hopcape.networking.api.request.strategy.RequestHandlerFactory
import com.hopcape.networking.api.request.strategy.RequestHandlingStrategy
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KtorClientNetworkingClientTest {

    private lateinit var mockClient: HttpClient
    private lateinit var client: KtorClientNetworkingClient

    @BeforeTest
    fun setup() {
        // Mock the HttpClient with MockEngine for testing
        mockClient = createMockClient("""{"data": "test"}""",HttpStatusCode.OK)

        // Initialize the client with the mocked dependencies

    }

    @Test
    fun `makeRequest should return a successful result`() = runBlocking {
        // Setup the mock response
        val expectedResult = MockSuccessResponse("user")
        val requestHandlerFactory = mock<RequestHandlerFactory>()
        val requestHandlingStrategy = mock<RequestHandlingStrategy>()
        val request = RequestBuilder().also {
            it.setUrl(Url("https://api.example.com"))
            it.setMethod(HttpMethod.GET)
        }.build()

        every {
            requestHandlerFactory.create(any(), any())
        } returns requestHandlingStrategy

        everySuspend {
            requestHandlingStrategy.handleRequest(request,MockSuccessResponse::class)
        } returns Result.success(expectedResult)

        client = KtorClientNetworkingClient(requestHandlerFactory = requestHandlerFactory)
        // Call the makeRequest method
        val result = client.makeRequest(
            responseType = MockSuccessResponse::class,
            requestBuilder = {
                setUrl(Url("https://api.example.com"))
                setMethod(HttpMethod.GET)
                build()
            }
        )

        // Assert that the result is successful
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(),expectedResult)
    }

    @Test
    fun `makeRequest should return a failure result on exception`() = runBlocking {
        val exception = IllegalStateException("Interval server error")
        val requestHandlerFactory = mock<RequestHandlerFactory>()
        val requestHandlingStrategy = mock<RequestHandlingStrategy>()
        val request = RequestBuilder().also {
            it.setUrl(Url("https://api.example.com"))
            it.setMethod(HttpMethod.GET)
        }.build()

        every {
            requestHandlerFactory.create(any(), any())
        } returns requestHandlingStrategy

        everySuspend {
            requestHandlingStrategy.handleRequest(request,MockSuccessResponse::class)
        } returns Result.failure(exception)

        client = KtorClientNetworkingClient(requestHandlerFactory = requestHandlerFactory)
        // Call the makeRequest method
        val result = client.makeRequest(
            responseType = MockSuccessResponse::class,
            requestBuilder = {
                setUrl(Url("https://api.example.com"))
                setMethod(HttpMethod.GET)
                build()
            }
        )

        // Assert that the result is successful
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull(),exception)
    }
}
