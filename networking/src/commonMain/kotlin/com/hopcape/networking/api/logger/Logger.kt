package com.hopcape.networking.api.logger

/**
 * A functional interface representing a logger that outputs log messages.
 *
 * This interface defines a single method `log()` that accepts a string message
 * and outputs it in a way that can be implemented for various logging mechanisms
 * (e.g., console logging, file logging, etc.).
 *
 * ## Example Usage:
 * ```kotlin
 * val logger = Logger { message -> println("Log: $message") }
 * logger.log("This is a log message.")
 * ```
 *
 * @author Murtaza Khursheed
 */
fun interface Logger {
    /**
     * Logs the provided message.
     *
     * @param message The message to be logged.
     */
    fun log(message: String)
}
