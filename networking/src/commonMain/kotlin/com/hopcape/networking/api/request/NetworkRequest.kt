package com.hopcape.networking.api.request

import com.hopcape.networking.api.request.methods.HttpMethod

/**
 * Data class representing a network request.
 *
 * This class encapsulates all the necessary details required to make an HTTP request. It supports various
 * HTTP methods (e.g., GET, POST) and allows customization of headers, query parameters, and request bodies.
 * The class is designed to be flexible and reusable across different types of network requests.
 *
 * ## Key Features:
 * - Encapsulates the URL, HTTP method, headers, body, and query parameters in a single object.
 * - Provides default values for optional properties to simplify usage in common scenarios.
 * - Supports serialization-friendly objects as the request body, making it compatible with Kotlinx Serialization.
 *
 * ## Example Usage:
 * ```kotlin
 * val request = NetworkRequest(
 *     url = "https://api.example.com/data",
 *     method = HttpMethod.POST,
 *     requestHeaders = mapOf("Authorization" to "Bearer token"),
 *     requestBody = mapOf("key" to "value"),
 *     params = mapOf("filter" to "active")
 * )
 * ```
 *
 * @author Murtaza Khursheed
 */
data class NetworkRequest(
    /**
     * The URL for the HTTP request.
     *
     * This is the endpoint where the request will be sent. It should include the full path to the resource,
     * including the protocol (e.g., `http://` or `https://`) and any base path or resource-specific segments.
     *
     * ### Example:
     * ```kotlin
     * val url = "https://api.example.com/users"
     * ```
     *
     * Defaults to an empty string (`""`), which represents an invalid or unspecified URL.
     */
    val url: String = "",

    /**
     * The HTTP method to be used for the request.
     *
     * This specifies the type of operation being performed on the resource. Common methods include:
     * - `GET`: Retrieve data from the server.
     * - `POST`: Send data to the server to create or update a resource.
     * - `PUT`: Update an existing resource on the server.
     * - `DELETE`: Remove a resource from the server.
     *
     * ### Example:
     * ```kotlin
     * val method = HttpMethod.POST
     * ```
     *
     * Defaults to [HttpMethod.GET], which is the most commonly used HTTP method.
     */
    val method: HttpMethod = HttpMethod.GET,

    /**
     * An optional map of headers to include in the request.
     *
     * Headers provide additional information about the request, such as authentication tokens, content types,
     * or custom metadata. Each key-value pair represents a header name and its corresponding value.
     *
     * ### Example:
     * ```kotlin
     * val headers = mapOf(
     *     "Authorization" to "Bearer token",
     *     "Content-Type" to "application/json"
     * )
     * ```
     *
     * Defaults to `null`, meaning no headers are included by default.
     */
    val requestHeaders: Map<String, String>? = null,

    /**
     * An optional body content for the request.
     *
     * This property represents the payload sent with the request, typically used with methods like `POST` or `PUT`.
     * The body can be any serializable object, such as a `Map`, `String`, or custom data class. If the body is a `Map`,
     * it will typically be serialized into JSON format before being sent.
     *
     * ### Example:
     * ```kotlin
     * val body = mapOf("username" to "user123", "password" to "password123")
     * ```
     *
     * Defaults to `null`, meaning no body is included by default.
     */
    val requestBody: Any? = null,

    /**
     * An optional map of query parameters to append to the URL.
     *
     * Query parameters are used to filter, sort, or paginate resources. Each key-value pair represents a parameter
     * name and its corresponding value. These parameters are appended to the URL after a `?` character, separated by `&`.
     *
     * ### Example:
     * ```kotlin
     * val params = mapOf(
     *     "page" to "1",
     *     "limit" to "10",
     *     "filter" to "active"
     * )
     * ```
     *
     * Defaults to `null`, meaning no query parameters are included by default.
     */
    val params: Map<String, String>? = null,
)