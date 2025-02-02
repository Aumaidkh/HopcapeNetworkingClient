package com.hopcape.networking.api.request

import com.hopcape.networking.api.Url
import com.hopcape.networking.api.request.methods.HttpMethod

/**
 * A builder class to help construct a `NetworkRequest` object with optional configurations.
 *
 * This class provides a convenient way to set different properties of a `NetworkRequest` such as the URL,
 * HTTP method, headers, and body. It uses a fluent interface to allow chaining of methods, making it easy
 * to build a customized `NetworkRequest`.
 *
 * ## How it Works:
 * - Use `setUrl()` to specify the URL for the request.
 * - Use `setMethod()` to set the HTTP method (e.g., `GET`, `POST`).
 * - Use `setHeaders()` to set any headers for the request.
 * - Use `setBody()` to specify the request body if needed.
 * - Finally, use `build()` to retrieve the constructed `NetworkRequest`.
 *
 * ## Example Usage:
 * ```kotlin
 * val request = com.hopcape.networking.api.request.RequestBuilder()
 *     .setUrl(Url("https://api.example.com"))
 *     .setMethod(HttpMethod.GET)
 *     .setHeaders(mapOf("Authorization" to "Bearer token"))
 *     .setBody("request body")
 *     .build()
 * ```
 *
 * @constructor Creates a new `RequestBuilder` instance.
 * @author Murtaza Khursheed
 */
class RequestBuilder {
    private var request: NetworkRequest = NetworkRequest()

    /**
     * Sets the URL for the network request.
     *
     * @param url The URL to be used in the request.
     * @return The updated `RequestBuilder` instance.
     */
    fun setUrl(url: Url): RequestBuilder {
        request = request.copy(url = url.value)
        return this
    }

    /**
     * Sets the HTTP method for the network request.
     *
     * @param method The HTTP method (e.g., `GET`, `POST`) to be used in the request.
     * @return The updated `RequestBuilder` instance.
     */
    fun setMethod(method: HttpMethod): RequestBuilder {
        request = request.copy(method = method)
        return this
    }

    /**
     * Sets the headers for the network request.
     *
     * @param headers The headers to be included in the request.
     * @return The updated `RequestBuilder` instance.
     */
    fun setHeaders(headers: Map<String, String>): RequestBuilder {
        request = request.copy(requestHeaders = headers)
        return this
    }

    /**
     * Sets the body for the network request.
     *
     * @param body The body to be included in the request.
     * @return The updated `RequestBuilder` instance.
     */
    fun setBody(body: String): RequestBuilder {
        request = request.copy(requestBody = body)
        return this
    }

    /**
     * Builds and returns the constructed `NetworkRequest` instance.
     *
     * @return The final `NetworkRequest` instance with all the configurations set.
     */
    fun build() = request
}
