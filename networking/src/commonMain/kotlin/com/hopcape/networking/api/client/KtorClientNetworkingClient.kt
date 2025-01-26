package com.hopcape.networking.api.client

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.RequestBuilder
import com.hopcape.networking.api.request.RequestHandlerFactory
import io.ktor.client.HttpClient
import kotlin.reflect.KClass

internal class KtorClientNetworkingClient(
    private val client: HttpClient,
    private val configuration: Configuration
): NetworkingClient {

    override suspend fun <T : Any> makeRequest(
        responseType: KClass<T>,
        requestBuilder: RequestBuilder.() -> NetworkRequest
    ): Result<T> {
        val newRequestBuilder = RequestBuilder()
        return RequestHandlerFactory(
            client = client,
            configuration = configuration
        ).create(newRequestBuilder.requestBuilder().method)
            .handleRequest(newRequestBuilder.requestBuilder(),responseType)
    }
}