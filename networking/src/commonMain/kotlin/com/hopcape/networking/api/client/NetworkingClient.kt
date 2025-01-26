package com.hopcape.networking.api.client

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.di.NetworkingDependencyContainer
import com.hopcape.networking.api.di.NetworkingModuleDependencyFactory
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.RequestBuilder
import kotlin.reflect.KClass

interface NetworkingClient {

    suspend fun <T: Any> makeRequest(
        responseType: KClass<T>,
        requestBuilder: RequestBuilder.() -> NetworkRequest
    ): Result<T>

    companion object {

        fun configure(
            configuration: Configuration
        ){
            NetworkingDependencyContainer.initialize(
                factory = NetworkingModuleDependencyFactory(configuration)
            )
        }

        fun getInstance(): NetworkingClient {
            return NetworkingDependencyContainer.get(NetworkingClient::class)
        }
    }
}