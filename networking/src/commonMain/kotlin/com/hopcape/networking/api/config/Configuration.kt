package com.hopcape.networking.api.config

import com.hopcape.networking.api.logger.consoleLogger

data class Configuration(
    val logger: (String) -> Unit = {
        consoleLogger.log(it)
    }
)
