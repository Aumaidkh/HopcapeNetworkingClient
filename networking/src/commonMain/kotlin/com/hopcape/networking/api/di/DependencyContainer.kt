package com.hopcape.networking.api.di

import kotlin.reflect.KClass

internal interface DependencyContainer {
    fun initialize(factory: DependencyFactory)
    fun  <T: Any> get(type: KClass<T>): T
}