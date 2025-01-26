package com.hopcape.networking.api.request.handlers

import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.strategy.RequestHandlingStrategy
import com.hopcape.networking.api.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

internal class PostRequestHandlerStrategy(
    private val client: HttpClient,
    private val logger: (String) -> Unit = {}
): RequestHandlingStrategy {

    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> handleRequest(
        request: NetworkRequest,
        type: KClass<T>
    ): Result<T> {
        return safeApiCall(
            logger = logger,
            apiCall = {
                with(request){
                    val response = client.post(url) {
                        // Add headers
                        requestHeaders?.forEach { (key, value) ->
                            header(key, value)
                        }
                        // Add query parameters
                        params?.forEach { (key, value) ->
                            url {
                                parameters.append(key, value)
                            }
                        }
                    }.bodyAsText()
                    // Now you can use .body() with T as a reified type
                    val json = Json { ignoreUnknownKeys = true }
                    json.decodeFromString(type.serializer(), response)
                }
            }
        )
    }
}