package com.hopcape.networking.api.request.strategy

import com.hopcape.networking.api.request.NetworkRequest
import kotlin.reflect.KClass

/**
 * Interface for handling network requests with different strategies.
 *
 * This interface defines a strategy for handling network requests. Implementations of this interface should define
 * how requests are processed and return the results. The `handleRequest` method is responsible for managing the request
 * and returning the appropriate result asynchronously.
 *
 * ## Example Usage:
 * ```kotlin
 * val requestHandlingStrategy: com.hopcape.networking.api.request.strategy.RequestHandlingStrategy = // some implementation
 * val result = requestHandlingStrategy.handleRequest(request, SomeResponse::class)
 * ```
 *
 * @author Murtaza Khursheed
 */
interface RequestHandlingStrategy {

    /**
     * Handles a network request and returns the result.
     *
     * @param request The network request to be processed.
     * @param type The expected type of the response.
     * @return A `Result` containing the response of type `T`.
     */
    suspend fun <T: Any> handleRequest(request: NetworkRequest, type: KClass<T>): Result<T>
}
