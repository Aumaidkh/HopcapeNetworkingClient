package com.hopcape.networking

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform