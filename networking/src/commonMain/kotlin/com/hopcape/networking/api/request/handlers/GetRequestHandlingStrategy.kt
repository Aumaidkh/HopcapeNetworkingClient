package com.hopcape.networking.api.request.handlers

import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.strategy.RequestHandlingStrategy
import com.hopcape.networking.api.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * A strategy implementation for handling GET network requests.
 *
 * This class is responsible for handling HTTP GET requests using the provided `HttpClient`. It supports setting request headers
 * and query parameters as per the `com.hopcape.networking.api.request.NetworkRequest` provided. The response is expected to be a JSON string, which is then
 * deserialized into the specified type `T`.
 *
 * ## Example Usage:
 * ```kotlin
 * val client = HttpClient()
 * val logger: (String) -> Unit = { println(it) }
 * val strategy = com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy(client, logger)
 * val result = strategy.handleRequest(request, SomeResponse::class)
 * ```
 *
 * @property client The `HttpClient` instance used to make the network request.
 * @property logger A logger function that handles logging (optional).
 */
internal class GetRequestHandlingStrategy(
    private val client: HttpClient,
    private val logger: (String) -> Unit = {}
) : RequestHandlingStrategy {

    /**
     * Handles a GET network request and returns the response as an object of type `T`.
     *
     * This method makes the GET request, appends the necessary headers and parameters, and processes the response to
     * deserialize it into the specified type `T`.
     *
     * @param request The `com.hopcape.networking.api.request.NetworkRequest` containing the URL, headers, and parameters for the GET request.
     * @param type The type `KClass<T>` that specifies the expected response type.
     * @return A `Result` containing the deserialized response of type `T`.
     */
    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> handleRequest(
        request: NetworkRequest,
        type: KClass<T>
    ): Result<T> {
        return safeApiCall(
            logger = logger,
            apiCall = {
                with(request) {
                    val response = client.get(url) {
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
                        // Add body
                        requestBody?.let {
                            setBody(it)
                        }
                    }.bodyAsText()
                    // Deserialize response to type `T`
                    val json = Json { ignoreUnknownKeys = true }
                    json.decodeFromString(type.serializer(), response)
                }
            }
        )
    }
}
