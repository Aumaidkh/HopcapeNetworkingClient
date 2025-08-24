package com.hopcape.networking.api.request.strategy

import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.methods.HttpMethod

/**
 * A factory interface responsible for creating instances of [RequestHandlingStrategy]
 * based on the provided [HttpMethod] type and optionally the request content.
 *
 * The factory ensures the appropriate handling strategy is returned for each HTTP method
 * (e.g., GET, POST, PUT, DELETE) that is required for the network request. It can also
 * analyze the request content to determine if special handling (like multipart for file uploads) is needed.
 *
 * @see RequestHandlingStrategy
 * @see HttpMethod
 * @see NetworkRequest
 */
interface RequestHandlerFactory {

    /**
     * Creates a [RequestHandlingStrategy] based on the provided HTTP method type.
     *
     * @param type The [HttpMethod] that will guide the creation of the corresponding strategy.
     * @return A [RequestHandlingStrategy] that matches the provided HTTP method.
     */
    fun create(type: HttpMethod): RequestHandlingStrategy

    /**
     * Creates a [RequestHandlingStrategy] based on the provided HTTP method type and request content.
     * This method allows the factory to analyze the request content to determine the most appropriate
     * handling strategy (e.g., multipart for file uploads).
     *
     * @param type The [HttpMethod] that will guide the creation of the corresponding strategy.
     * @param request The [NetworkRequest] to analyze for content type determination.
     * @return A [RequestHandlingStrategy] that matches the provided HTTP method and content.
     */
    fun create(type: HttpMethod, request: NetworkRequest): RequestHandlingStrategy
}
