package com.hopcape.networking.api.config

import com.hopcape.networking.api.logger.consoleLogger

/**
 * A data class representing the configuration for the networking module.
 *
 * This class holds configuration settings for the networking client, such as the logger function used to log messages.
 * By default, the `logger` property is set to the `consoleLogger`, which outputs messages to the console.
 *
 * ## How it Works:
 * - The `logger` property accepts a lambda function that takes a `String` message and outputs it.
 * - The default logger used is `consoleLogger`, which logs to the console.
 *
 * ## Example Usage:
 * ```kotlin
 * val config = Configuration()
 * config.logger("This is a log message")
 * ```
 * This will log the message to the console using the default `consoleLogger`.
 *
 * @property logger A lambda function used to log messages. Defaults to the `consoleLogger` function.
 *
 * @constructor Creates a new `Configuration` instance with the specified logger.
 * If no logger is provided, the default `consoleLogger` is used.
 */
data class Configuration(
    val logger: (String) -> Unit = {
        consoleLogger.log(it)
    }
)
