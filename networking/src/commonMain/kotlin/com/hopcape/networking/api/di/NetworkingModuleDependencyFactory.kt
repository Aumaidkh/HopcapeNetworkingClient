package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.KtorClientNetworkingClient
import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class NetworkingModuleDependencyFactory(
    private val configuration: Configuration
): DependencyFactory {

    override fun createNetworkingClient(): NetworkingClient {
        return KtorClientNetworkingClient(
            client = HttpClient{
                install(ContentNegotiation){
                    json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
                }
            },
            configuration = configuration
        )
    }

    override fun createConfiguration(): Configuration {
        return configuration
    }
}