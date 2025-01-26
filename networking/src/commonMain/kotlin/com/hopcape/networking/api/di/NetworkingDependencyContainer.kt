package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration
import kotlin.reflect.KClass

internal object NetworkingDependencyContainer: DependencyContainer {
    private lateinit var dependencyGraph: MutableMap<KClass<*>, Any>

    override fun initialize(factory: DependencyFactory) {
        dependencyGraph = mutableMapOf()
        with(factory){
            dependencyGraph.put(NetworkingClient::class,createNetworkingClient())
            dependencyGraph.put(Configuration::class,createConfiguration())
        }
    }

    override fun <T : Any> get(type: KClass<T>): T {
        if (!::dependencyGraph.isInitialized){
            throw IllegalStateException("Dependency graph not initialized")
        }
        if (dependencyGraph.containsKey(type)){
            return dependencyGraph[type] as T
        }
        throw IllegalStateException("No dependency found for type: $type")
    }
}