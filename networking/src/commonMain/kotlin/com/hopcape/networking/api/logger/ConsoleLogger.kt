package com.hopcape.networking.api.logger

/**
 * A logger implementation that outputs log messages to the console.
 *
 * This logger prepends the message with the prefix "HopcapeNetworking:" and prints it
 * to the standard output using `println()`. It is intended for use in scenarios
 * where simple console logging is needed for debugging or development purposes.
 *
 * ## Example Usage:
 * ```kotlin
 * consoleLogger.log("This is a log message.")
 * // Output: HopcapeNetworking: This is a log message.
 * ```
 *
 * This logger is internal to the library, and it is intended for use within the
 * `com.hopcape.networking` package.
 *
 * @see Logger
 */
internal val consoleLogger = Logger {
    println("HopcapeNetworking: $it")
}
