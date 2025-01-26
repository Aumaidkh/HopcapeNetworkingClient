package com.hopcape.networking.api.di

import com.hopcape.networking.api.client.NetworkingClient
import com.hopcape.networking.api.config.Configuration
import kotlin.reflect.KClass

/**
 * A singleton implementation of [DependencyContainer] for managing networking-related dependencies.
 *
 * This object is responsible for initializing and providing instances of key dependencies like
 * `NetworkingClient` and `Configuration`. It stores these dependencies in a `dependencyGraph`, which
 * is a map of types to their respective instances. The `NetworkingDependencyContainer` is designed
 * to work with a `DependencyFactory` to create and store instances of the necessary components.
 *
 * ## How it Works:
 * - Use `initialize()` to set up the dependency graph with the necessary dependencies.
 * - Use `get()` to retrieve a specific dependency by its type.
 *
 * ## Example Usage:
 * ```kotlin
 * val factory: DependencyFactory = SomeDependencyFactory()
 * NetworkingDependencyContainer.initialize(factory)
 * val networkingClient: NetworkingClient = NetworkingDependencyContainer.get(NetworkingClient::class)
 * val configuration: Configuration = NetworkingDependencyContainer.get(Configuration::class)
 * ```
 *
 * @see DependencyContainer
 * @see DependencyFactory
 * @see NetworkingClient
 * @see Configuration
 *
 * @author Murtaza Khursheed
 */
internal object NetworkingDependencyContainer : DependencyContainer {

    private lateinit var dependencyGraph: MutableMap<KClass<*>, Any>

    /**
     * Initializes the dependency container by creating and storing dependencies using the given [factory].
     *
     * This method sets up the internal `dependencyGraph` by calling the [DependencyFactory] to create
     * and store instances of the necessary dependencies like `NetworkingClient` and `Configuration`.
     *
     * @param factory The [DependencyFactory] responsible for creating the dependencies.
     */
    override fun initialize(factory: DependencyFactory) {
        dependencyGraph = mutableMapOf()
        with(factory) {
            dependencyGraph.put(NetworkingClient::class, createNetworkingClient())
            dependencyGraph.put(Configuration::class, createConfiguration())
        }
    }

    /**
     * Retrieves a dependency of the specified type.
     *
     * This method looks up and returns an instance of the requested dependency from the `dependencyGraph`.
     * If the dependency has not been initialized, an [IllegalStateException] will be thrown.
     *
     * @param type The type of the dependency to retrieve.
     * @return The requested dependency instance.
     * @throws IllegalStateException if the dependency graph is not initialized or the dependency is not found.
     */
    override fun <T : Any> get(type: KClass<T>): T {
        if (!::dependencyGraph.isInitialized) {
            throw IllegalStateException("Dependency graph not initialized")
        }
        if (dependencyGraph.containsKey(type)) {
            return dependencyGraph[type] as T
        }
        throw IllegalStateException("No dependency found for type: $type")
    }
}
