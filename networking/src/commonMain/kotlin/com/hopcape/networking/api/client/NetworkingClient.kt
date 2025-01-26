package com.hopcape.networking.api.client

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.di.NetworkingDependencyContainer
import com.hopcape.networking.api.di.NetworkingModuleDependencyFactory
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.RequestBuilder
import kotlin.reflect.KClass

/**
 * The `NetworkingClient` interface provides methods for making network requests with the configured HTTP client.
 * It is used to interact with APIs and receive responses by providing a generic and reusable way of making requests.
 *
 * ## Usage:
 * - Use the `makeRequest()` method to make a network request, passing the desired response type and a lambda
 *   to configure the `NetworkRequest`.
 * - Use `NetworkingClient.configure()` to initialize the `NetworkingDependencyContainer` with a `Configuration`
 *   object that holds the necessary configurations (such as logger and other settings).
 * - Access the singleton instance of `NetworkingClient` using `NetworkingClient.getInstance()`.
 *
 * ## Example Usage:
 * ```kotlin
 * // Configure the client
 * NetworkingClient.configure(configuration)
 *
 * // Making a request to fetch user data
 * val response = NetworkingClient.getInstance().makeRequest(User::class) {
 *     setUrl(Url("https://api.example.com/user"))
 *     setMethod(HttpMethod.GET)
 * }
 * ```
 *
 * @see RequestBuilder for building the `NetworkRequest`.
 * @see Configuration for the configuration options.
 */
interface NetworkingClient {

    /**
     * Makes an asynchronous network request and returns a result of the specified response type.
     *
     * @param responseType The expected type of the response. It is a `KClass` type, which will be used for
     *                     parsing the response.
     * @param requestBuilder A lambda function used to configure the `NetworkRequest` object, which includes
     *                       setting the URL, HTTP method, headers, and body.
     *
     * @return A `Result` wrapper containing either the parsed response of type `T` or an error.
     */
    suspend fun <T: Any> makeRequest(
        responseType: KClass<T>,
        requestBuilder: RequestBuilder.() -> NetworkRequest
    ): Result<T>

    companion object {

        /**
         * Configures the `NetworkingClient` by initializing the dependency container with the provided configuration.
         *
         * @param configuration The configuration object that contains various setup options such as logging.
         */
        fun configure(
            configuration: Configuration
        ){
            NetworkingDependencyContainer.initialize(
                factory = NetworkingModuleDependencyFactory(configuration)
            )
        }

        /**
         * Retrieves the singleton instance of the `NetworkingClient`.
         *
         * @return The instance of the `NetworkingClient`.
         */
        fun getInstance(): NetworkingClient {
            return NetworkingDependencyContainer.get(NetworkingClient::class)
        }
    }
}
