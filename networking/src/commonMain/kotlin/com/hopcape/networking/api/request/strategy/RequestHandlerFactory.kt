package com.hopcape.networking.api.request.strategy

import com.hopcape.networking.api.request.methods.HttpMethod

/**
 * A factory interface responsible for creating instances of [RequestHandlingStrategy]
 * based on the provided [HttpMethod] type.
 *
 * The factory ensures the appropriate handling strategy is returned for each HTTP method
 * (e.g., GET, POST, PUT, DELETE) that is required for the network request.
 *
 * @property type The [HttpMethod] type that determines which [RequestHandlingStrategy]
 * will be created.
 *
 * @see RequestHandlingStrategy
 * @see HttpMethod
 */
fun interface RequestHandlerFactory {

    /**
     * Creates a [RequestHandlingStrategy] based on the provided HTTP method type.
     *
     * @param type The [HttpMethod] that will guide the creation of the corresponding strategy.
     * @return A [RequestHandlingStrategy] that matches the provided HTTP method.
     */
    fun create(type: HttpMethod): RequestHandlingStrategy
}
