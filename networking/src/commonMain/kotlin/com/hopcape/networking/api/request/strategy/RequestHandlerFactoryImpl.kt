package com.hopcape.networking.api.request.strategy

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.request.methods.HttpMethod
import com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy
import com.hopcape.networking.api.request.handlers.PostRequestHandlerStrategy
import com.hopcape.networking.api.utils.HopcapeOpenAnnotation
import io.ktor.client.HttpClient

/**
 * Factory class that creates the appropriate request handling strategy based on the HTTP method type.
 *
 * This class is responsible for providing the correct implementation of the `com.hopcape.networking.api.request.strategy.RequestHandlingStrategy`
 * depending on whether the request type is `GET` or `POST`. The `com.hopcape.networking.api.request.strategy.RequestHandlerFactory` takes an `HttpClient`
 * and a `Configuration` as constructor parameters and uses them to configure the appropriate handler with necessary
 * dependencies, such as logging. It is designed to handle `GET` and `POST` requests but can be extended to support
 * other HTTP methods if needed.
 *
 * ## How it Works:
 * - The factory uses the HTTP method type (`com.hopcape.networking.api.request.methods.HttpMethod.GET` or `com.hopcape.networking.api.request.methods.HttpMethod.POST`) to determine which handler to create.
 * - It creates a `com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy` for `GET` requests and a `com.hopcape.networking.api.request.handlers.PostRequestHandlerStrategy` for `POST` requests.
 * - If an unsupported HTTP method is passed to the `create` method, it throws an `IllegalArgumentException`.
 *
 * ## Example Usage:
 * ```kotlin
 * val client = HttpClient()
 * val configuration = Configuration(logger = someLogger)  // Replace with actual logger
 *
 * // Instantiate the factory
 * val requestHandlerFactory = com.hopcape.networking.api.request.strategy.RequestHandlerFactory(client, configuration)
 *
 * // Create a GET request handler
 * val getHandler = requestHandlerFactory.create(com.hopcape.networking.api.request.methods.HttpMethod.GET)
 *
 * // Create a POST request handler
 * val postHandler = requestHandlerFactory.create(com.hopcape.networking.api.request.methods.HttpMethod.POST)
 * ```
 *
 * @author
 * Murtaza Khursheed
 */
@HopcapeOpenAnnotation
internal class RequestHandlerFactoryImpl(
    private val client: HttpClient,
    private val configuration: Configuration
): RequestHandlerFactory {
    /**
     * Creates a request handling strategy based on the provided HTTP method type.
     *
     * @param type The HTTP method type (GET or POST).
     * @return An implementation of `com.hopcape.networking.api.request.strategy.RequestHandlingStrategy` for the specified method type.
     *
     * @throws IllegalArgumentException If an unsupported HTTP method type is passed.
     */
    override fun create(type: HttpMethod): RequestHandlingStrategy {
        return when (type) {
            HttpMethod.GET -> GetRequestHandlingStrategy(
                client = client,
                logger = configuration.logger
            )
            HttpMethod.POST -> PostRequestHandlerStrategy(
                client = client,
                logger = configuration.logger
            )
            else -> throw IllegalArgumentException("Can't handle Request Type: ${type.name}")
        }
    }
}