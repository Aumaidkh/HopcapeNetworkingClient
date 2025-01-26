package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.KtorClientNetworkingClient
import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.request.methods.HttpMethod
import com.hopcape.networking.api.request.strategy.RequestHandlerFactory
import com.hopcape.networking.api.request.strategy.RequestHandlerFactoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * A factory class for creating instances of `NetworkingClient` and `Configuration` for the networking module.
 *
 * This class is responsible for providing concrete implementations of the `NetworkingClient` and `Configuration`
 * based on the provided configuration.
 *
 * It utilizes `KtorClientNetworkingClient` to create a networking client using Ktor's HTTP client with content negotiation
 * and JSON serialization enabled. The `Configuration` is passed directly as it is provided in the constructor.
 *
 * ## How it Works:
 * - The `createNetworkingClient()` method returns an instance of `NetworkingClient` configured with Ktor's `HttpClient`.
 * - The `createConfiguration()` method simply returns the provided `Configuration` object.
 *
 * ## Example Usage:
 * ```kotlin
 * val configuration = Configuration()
 * val factory = NetworkingModuleDependencyFactory(configuration)
 *
 * val networkingClient = factory.createNetworkingClient()
 * val config = factory.createConfiguration()
 * ```
 *
 * @property configuration The configuration to be used for the networking client.
 *
 * @constructor Creates a new `NetworkingModuleDependencyFactory` instance with the provided configuration.
 *
 * @see NetworkingClient
 * @see KtorClientNetworkingClient
 * @see Configuration
 */
internal class NetworkingModuleDependencyFactory(
    private val configuration: Configuration
): DependencyFactory {

    /**
     * Creates and returns an instance of `NetworkingClient` using Ktor's `HttpClient`.
     *
     * The `HttpClient` is configured with content negotiation for JSON, using `Kotlinx Serialization` to handle
     * JSON serialization and deserialization with the option to ignore unknown keys.
     *
     * @return A new instance of `NetworkingClient`.
     */
    override fun createNetworkingClient(): NetworkingClient {
        return KtorClientNetworkingClient(
            requestHandlerFactory = RequestHandlerFactoryImpl(
                client = HttpClient {
                    install(ContentNegotiation) {
                        json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
                    }
                },
                configuration = configuration
            ),
        )
    }

    /**
     * Returns the provided `Configuration` instance.
     *
     * @return The configuration used for creating the networking client.
     */
    override fun createConfiguration(): Configuration {
        return configuration
    }
}
