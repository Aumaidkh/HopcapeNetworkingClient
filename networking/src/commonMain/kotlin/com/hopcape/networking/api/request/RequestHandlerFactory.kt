package com.hopcape.networking.api.request

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.request.handlers.GetRequestHandlingStrategy
import com.hopcape.networking.api.request.handlers.PostRequestHandlerStrategy
import io.ktor.client.HttpClient

internal class RequestHandlerFactory(
    private val client: HttpClient,
    private val configuration: Configuration
){
    fun create(type: HttpMethod): RequestHandlingStrategy {
        return when (type) {
            HttpMethod.GET -> GetRequestHandlingStrategy(
                client = client,
                logger = configuration.logger
            )
            HttpMethod.POST -> PostRequestHandlerStrategy(
                client = client,
                logger = configuration.logger
            )
            else -> throw IllegalArgumentException("Can't handle Request Type: ${type.name}")
        }
    }
}