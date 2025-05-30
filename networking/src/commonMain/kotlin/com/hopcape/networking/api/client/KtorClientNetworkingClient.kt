package com.hopcape.networking.api.client

import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.RequestBuilder
import com.hopcape.networking.api.request.strategy.RequestHandlerFactory
import kotlin.reflect.KClass

/**
 * # KtorClientNetworkingClient - A Networking Client for API Requests
 *
 * This class is an implementation of [NetworkingClient] that facilitates making HTTP network
 * requests using **Ktor**. It utilizes a **RequestHandlerFactory** to determine the appropriate
 * request strategy based on the **HTTP method**.
 *
 * ## 🔹 How It Works
 * 1. The `makeRequest` function constructs a **NetworkRequest** using the provided `requestBuilder` lambda.
 * 2. It determines the appropriate **request handling strategy** using [RequestHandlerFactory].
 * 3. The request is executed, and the response is deserialized into the specified type [T].
 * 4. The result is returned inside a `Result<T>` wrapper.
 *
 * ## 🔹 Example Usage
 * ```kotlin
 * data class UserResponse(val id: Int, val name: String)
 *
 * val client: NetworkingClient = KtorClientNetworkingClient(requestHandlerFactory)
 *
 * suspend fun fetchUser(): Result<UserResponse> {
 *     return client.makeRequest(UserResponse::class) {
 *         url = "https://api.example.com/user"
 *         method = HttpMethod.GET
 *         headers["Authorization"] = "Bearer your_token_here"
 *     }
 * }
 *
 * suspend fun handleResponse() {
 *     when (val result = fetchUser()) {
 *         is Result.Success -> println("User: ${result.value}")
 *         is Result.Failure -> println("Error: ${result.exception.message}")
 *     }
 * }
 * ```
 *
 * @property requestHandlerFactory Factory that creates a request handler based on the HTTP method.
 *
 * @author Murtaza Khursheed
 */
internal class KtorClientNetworkingClient(
    private val requestHandlerFactory: RequestHandlerFactory
) : NetworkingClient {

    /**
     * Executes a network request asynchronously and returns a [Result] containing either
     * the parsed response of type [T] or an error.
     *
     * This method constructs a [NetworkRequest] using the provided [requestBuilder] lambda
     * and determines the appropriate request handler using the [RequestHandlerFactory].
     *
     * @param T The expected response type.
     * @param responseType The [KClass] of the expected response type, used for deserialization.
     * @param requestBuilder A lambda function to configure the request parameters, such as
     *                       URL, HTTP method, headers, and body.
     *
     * @return A [Result] wrapping the response of type [T] if successful, or an error otherwise.
     *
     * @throws Exception if the network request fails due to connectivity issues or API errors.
     */
    override suspend fun <T : Any> makeRequest(
        responseType: KClass<T>,
        requestBuilder: RequestBuilder.() -> NetworkRequest
    ): Result<T> {
        val newRequestBuilder = RequestBuilder()
        return requestHandlerFactory.create(newRequestBuilder.requestBuilder().method)
            .handleRequest(newRequestBuilder.requestBuilder(), responseType)
    }
}
