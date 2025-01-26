package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.KtorClientNetworkingClient
import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.mocks.mockConfiguration
import dev.mokkery.mock
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DependencyFactoryTest {

    private lateinit var factory: NetworkingModuleDependencyFactory

    @BeforeTest
    fun setup() {
        // Create the NetworkingModuleDependencyFactory with the mocked configuration
        factory = NetworkingModuleDependencyFactory(mockConfiguration)
    }

    @Test
    fun `test createNetworkingClient returns NetworkingClient instance`() {
        // Call createNetworkingClient method
        val networkingClient: NetworkingClient = factory.createNetworkingClient()

        // Assert that the returned object is of type NetworkingClient
        assertNotNull(networkingClient)
        assertTrue(networkingClient is KtorClientNetworkingClient)

    }

    @Test
    fun `test createConfiguration returns correct configuration instance`() {
        // Call createConfiguration method
        val config: Configuration = factory.createConfiguration()

        // Assert that the returned object is the same as the mock configuration
        assertNotNull(config)
        assertTrue(config === mockConfiguration)
    }

}