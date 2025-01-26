package com.hopcape.networking.api.request.strategy

import com.hopcape.networking.api.request.NetworkRequest
import kotlin.reflect.KClass

interface RequestHandlingStrategy {
    suspend fun <T: Any> handleRequest(request: NetworkRequest, type: KClass<T>): Result<T>
}