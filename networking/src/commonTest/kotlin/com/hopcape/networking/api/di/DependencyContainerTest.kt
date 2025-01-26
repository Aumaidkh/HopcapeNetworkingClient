package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.mocks.mockConfiguration
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NetworkingDependencyContainerTest {

    private lateinit var mockFactory: DependencyFactory
    private lateinit var mockNetworkClient: NetworkingClient
    private lateinit var configuration : Configuration

    @BeforeTest
    fun setup() {
        // Create a mock DependencyFactory
        mockNetworkClient = mock()
        configuration = mockConfiguration
        mockFactory = mock()

        // Mock the creation of NetworkingClient and Configuration
        every {
            mockFactory.createNetworkingClient()
        } returns mockNetworkClient
        every {
            mockFactory.createConfiguration()
        } returns mockConfiguration
    }

    @Test
    fun `test get NetworkingClient`() {
        // Initialize the NetworkingDependencyContainer
        NetworkingDependencyContainer.initialize(mockFactory)
        val client: NetworkingClient = NetworkingDependencyContainer.get(NetworkingClient::class)
        assertNotNull(client)
        assertTrue(client is NetworkingClient)
    }

    @Test
    fun `test get Configuration`() {
        // Initialize the NetworkingDependencyContainer
        NetworkingDependencyContainer.initialize(mockFactory)
        val config: Configuration = NetworkingDependencyContainer.get(Configuration::class)
        assertNotNull(config)
        assertTrue(config is Configuration)
    }

    @Test
    fun `test get throws exception if dependency graph is not initialized`() {
        // Attempt to get a dependency without initializing it properly
        val exception = assertFailsWith<IllegalStateException>{
            NetworkingDependencyContainer.get(NetworkingClient::class)
        }
        assertNotNull(exception)
    }

    @Test
    fun `test get throws exception if dependency is not found`() {
        // Attempt to get a non-existent dependency
        val exception = assertFailsWith<IllegalStateException>{
            NetworkingDependencyContainer.get(NetworkingClient::class)
        }
        assertNotNull(exception)
    }
}
