package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration

internal interface DependencyFactory {
    fun createNetworkingClient(): NetworkingClient
    fun createConfiguration(): Configuration
}