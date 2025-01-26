package com.hopcape.networking.api.request.handlers

import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.strategy.RequestHandlingStrategy
import com.hopcape.networking.api.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * A strategy class for handling POST requests in the networking layer.
 *
 * This class implements the `RequestHandlingStrategy` interface and provides the functionality
 * to handle POST requests. It utilizes the Ktor `HttpClient` to send the request, adding the necessary headers
 * and query parameters if provided. The response is deserialized into the specified type `T` using Kotlinx Serialization.
 *
 * ## How it Works:
 * - The `handleRequest` method takes a `NetworkRequest` and the target type `T`.
 * - It performs a POST request using Ktor's `HttpClient` and processes the response.
 * - The response body is deserialized into the specified type `T` using the appropriate serializer.
 *
 * ## Example Usage:
 * ```kotlin
 * val client = HttpClient()
 * val configuration = Configuration(logger = someLogger) // Replace with actual logger
 *
 * // Instantiate the handler
 * val postHandler = com.hopcape.networking.api.request.handlers.PostRequestHandlerStrategy(client, configuration.logger)
 *
 * // Handle a POST request and get the response
 * val result = postHandler.handleRequest(networkRequest, SomeResponse::class)
 * ```
 *
 * @property client The Ktor `HttpClient` used to make the network request.
 * @property logger A logging function for logging API calls and responses (optional).
 *
 * @author Murtaza Khursheed
 */
internal class PostRequestHandlerStrategy(
    private val client: HttpClient,
    private val logger: (String) -> Unit = {}
): RequestHandlingStrategy {
    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> handleRequest(
        request: NetworkRequest,
        type: KClass<T>
    ): Result<T> {
        return safeApiCall(
            logger = logger,
            apiCall = {
                with(request){
                    val response = client.post(url) {
                        // Add headers
                        requestHeaders?.forEach { (key, value) ->
                            header(key, value)
                        }
                        // Add query parameters
                        params?.forEach { (key, value) ->
                            url {
                                parameters.append(key, value)
                            }
                        }
                    }.bodyAsText()
                    // Deserialize the response to the requested type
                    val json = Json { ignoreUnknownKeys = true }
                    json.decodeFromString(type.serializer(), response)
                }
            }
        )
    }
}
