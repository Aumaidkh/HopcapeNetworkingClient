package com.hopcape.networking.api.logger

internal val consoleLogger = Logger {
    println("HopcapeNetworking: $it")
}