package com.hopcape.networking.api.request

import com.hopcape.networking.api.request.methods.HttpMethod

/**
 * Data class representing a network request.
 *
 * This class is used to encapsulate the necessary details for making an HTTP request,
 * including the URL, HTTP method, request headers, request body, and query parameters.
 * It supports common HTTP methods defined in the `HttpMethod` enum, such as `GET`, `POST`, etc.
 *
 * ## Properties:
 * - `url`: The URL for the request.
 * - `method`: The HTTP method to be used (defaults to `HttpMethod.GET`).
 * - `requestHeaders`: Optional map of headers to include in the request.
 * - `body`: Optional body content for the request.
 * - `params`: Optional map of query parameters to append to the URL.
 *
 * ## Example Usage:
 * ```kotlin
 * val request = com.hopcape.networking.api.request.NetworkRequest(
 *     url = "https://api.example.com/data",
 *     method = HttpMethod.GET,
 *     requestHeaders = mapOf("Authorization" to "Bearer token"),
 *     params = mapOf("key" to "value")
 * )
 * ```
 *
 * @author Murtaza Khursheed
 */
data class NetworkRequest(
    val url: String = "",
    val method: HttpMethod = HttpMethod.GET,
    val requestHeaders: Map<String, String>? = null,
    val body: String? = null,
    val params: Map<String, String>? = null,
)
