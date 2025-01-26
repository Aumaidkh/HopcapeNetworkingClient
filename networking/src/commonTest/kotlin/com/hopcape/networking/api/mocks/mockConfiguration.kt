package com.hopcape.networking.api.mocks

import com.hopcape.networking.api.config.Configuration
import com.hopcape.networking.api.logger.consoleLogger

val mockConfiguration = Configuration(
    logger = {
        consoleLogger.log(it)
    }
)