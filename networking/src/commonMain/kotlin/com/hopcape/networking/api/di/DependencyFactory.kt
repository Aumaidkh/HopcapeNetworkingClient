package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration

/**
 * A factory interface responsible for creating various dependencies.
 *
 * This interface defines methods for creating core dependencies such as `NetworkingClient` and `Configuration`.
 * It is typically used to instantiate and configure the necessary components required by the application,
 * often as part of a dependency injection system.
 *
 * ## How it Works:
 * - Use `createNetworkingClient()` to create an instance of the `NetworkingClient`.
 * - Use `createConfiguration()` to create an instance of the `Configuration`.
 *
 * ## Example Usage:
 * ```kotlin
 * val factory: DependencyFactory = SomeDependencyFactory()
 * val networkingClient: NetworkingClient = factory.createNetworkingClient()
 * val configuration: Configuration = factory.createConfiguration()
 * ```
 *
 * @see NetworkingClient
 * @see Configuration
 *
 * @author Murtaza Khursheed
 */
internal interface DependencyFactory {

    /**
     * Creates and returns an instance of `NetworkingClient`.
     *
     * This method is responsible for creating the `NetworkingClient` instance that handles
     * the networking operations within the application.
     *
     * @return A new instance of `NetworkingClient`.
     */
    fun createNetworkingClient(): NetworkingClient

    /**
     * Creates and returns an instance of `Configuration`.
     *
     * This method is responsible for creating the `Configuration` instance that holds
     * the application's configuration settings.
     *
     * @return A new instance of `Configuration`.
     */
    fun createConfiguration(): Configuration
}
