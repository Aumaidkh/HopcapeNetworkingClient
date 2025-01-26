package com.hopcape.networking.api.di

import kotlin.reflect.KClass

/**
 * An interface representing a container for managing dependencies.
 *
 * This interface provides methods to initialize a dependency factory and retrieve
 * dependencies by their type. It is typically used in dependency injection scenarios
 * where the container is responsible for creating and providing instances of various types.
 *
 * ## How it Works:
 * - Use `initialize()` to set up the factory that will be responsible for creating dependencies.
 * - Use `get()` to retrieve a specific dependency by its type.
 *
 * ## Example Usage:
 * ```kotlin
 * val container: DependencyContainer = SomeDependencyContainer()
 * container.initialize(SomeDependencyFactory())
 * val someService: SomeService = container.get(SomeService::class)
 * ```
 *
 * @see DependencyFactory
 *
 * @author Murtaza Khursheed
 */
internal interface DependencyContainer {

    /**
     * Initializes the dependency container with a given factory.
     *
     * This method sets up the factory that will be responsible for creating and resolving dependencies.
     *
     * @param factory The factory that will be used for creating dependencies.
     */
    fun initialize(factory: DependencyFactory)

    /**
     * Retrieves a dependency of the specified type.
     *
     * This method looks up and returns an instance of the specified type from the container.
     * The type is determined at runtime using reflection.
     *
     * @param type The type of the dependency to retrieve.
     * @return An instance of the requested dependency.
     * @throws IllegalStateException if the dependency has not been initialized or is not available.
     */
    fun <T: Any> get(type: KClass<T>): T
}
