package com.hopcape.networking.api.request.strategy

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.request.methods.HttpMethod
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy
import com.hopcape.networking.api.request.handlers.PostRequestHandlerStrategy
import com.hopcape.networking.api.request.handlers.MultipartRequestHandlerStrategy
import com.hopcape.networking.api.utils.HopcapeOpenAnnotation
import io.ktor.client.HttpClient

/**
 * Factory class that creates the appropriate request handling strategy based on the HTTP method type and request content.
 *
 * This class is responsible for providing the correct implementation of the `com.hopcape.networking.api.request.strategy.RequestHandlingStrategy`
 * depending on whether the request type is `GET`, `POST`, or contains file uploads. The factory takes an `HttpClient`
 * and a `Configuration` as constructor parameters and uses them to configure the appropriate handler with necessary
 * dependencies, such as logging. It automatically detects when files are present and uses the multipart handler for those cases.
 *
 * ## How it Works:
 * - The factory uses the HTTP method type and request content to determine which handler to create.
 * - It creates a `com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy` for `GET` requests.
 * - For `POST` requests, it checks if files are present:
 *   - If files are present, it creates a `MultipartRequestHandlerStrategy` for file uploads.
 *   - If no files are present, it creates a `PostRequestHandlerStrategy` for regular POST requests.
 * - If an unsupported HTTP method is passed to the `create` method, it throws an `IllegalArgumentException`.
 *
 * ## Example Usage:
 * ```kotlin
 * val client = HttpClient()
 * val configuration = Configuration(logger = someLogger)  // Replace with actual logger
 *
 * // Instantiate the factory
 * val requestHandlerFactory = RequestHandlerFactoryImpl(client, configuration)
 *
 * // Create a GET request handler
 * val getHandler = requestHandlerFactory.create(HttpMethod.GET)
 *
 * // Create a POST request handler (regular)
 * val postHandler = requestHandlerFactory.create(HttpMethod.POST)
 *
 * // Create a multipart request handler (when files are present)
 * val multipartHandler = requestHandlerFactory.create(HttpMethod.POST, requestWithFiles)
 * ```
 *
 * @author Murtaza Khursheed
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

    /**
     * Creates a request handling strategy based on the provided HTTP method type and request content.
     * This method automatically detects when files are present and uses the multipart handler for those cases.
     *
     * @param type The HTTP method type (GET or POST).
     * @param request The network request to analyze for content type determination.
     * @return An implementation of `com.hopcape.networking.api.request.strategy.RequestHandlingStrategy` for the specified method type and content.
     *
     * @throws IllegalArgumentException If an unsupported HTTP method type is passed.
     */
    override fun create(type: HttpMethod, request: NetworkRequest): RequestHandlingStrategy {
        return when (type) {
            HttpMethod.GET -> GetRequestHandlingStrategy(
                client = client,
                logger = configuration.logger
            )
            HttpMethod.POST -> {
                // Check if files are present to determine the appropriate handler
                if (request.files?.isNotEmpty() == true) {
                    MultipartRequestHandlerStrategy(
                        client = client,
                        logger = configuration.logger
                    )
                } else {
                    PostRequestHandlerStrategy(
                        client = client,
                        logger = configuration.logger
                    )
                }
            }
            else -> throw IllegalArgumentException("Can't handle Request Type: ${type.name}")
        }
    }
}