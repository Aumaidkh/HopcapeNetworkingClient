package com.hopcape.networking.api.request.strategy

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.mocks.mockConfiguration
import com.hopcape.networking.api.mocks.mockEngine
import com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy
import com.hopcape.networking.api.request.handlers.PostRequestHandlerStrategy
import com.hopcape.networking.api.request.methods.HttpMethod
import dev.mokkery.mock
import io.ktor.client.HttpClient
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RequestHandlerFactoryTest {

    private val client: HttpClient = HttpClient(mockEngine)
    private val configuration: Configuration = mockConfiguration
    private val factory = RequestHandlerFactory(client, configuration)

    @Test
    fun `test creating GET request handler`() {
        val getHandler = factory.create(HttpMethod.GET)

        assertTrue(getHandler is GetRequestHandlingStrategy, "Expected com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy")
    }

    @Test
    fun `test creating POST request handler`() {
        val postHandler = factory.create(HttpMethod.POST)

        assertTrue(postHandler is PostRequestHandlerStrategy, "Expected com.hopcape.networking.api.request.handlers.PostRequestHandlerStrategy")
    }

    @Test
    fun `test creating handler with unsupported HTTP method`() {
        assertFailsWith<IllegalArgumentException> {
            factory.create(HttpMethod.PUT)  // Assuming PUT is unsupported
        }
    }
}
