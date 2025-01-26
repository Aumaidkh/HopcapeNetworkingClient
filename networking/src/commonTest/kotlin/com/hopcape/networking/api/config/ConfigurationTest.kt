package com.hopcape.networking.api.config

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

class ConfigurationTest {

    private lateinit var mockLogger: (String) -> Unit

    @BeforeTest
    fun setup() {
        // Mock the logger function
        mockLogger = mock()
    }

    @Test
    fun `test custom logger is used`() {
        // Create a Configuration instance with a custom logger
        val customLogger: (String) -> Unit = mockLogger
        every { customLogger("Custom log message") } returns Unit
        val config = Configuration(logger = customLogger)

        // Call the custom logger
        config.logger("Custom log message")

        // Verify the custom logger was called with the correct message
        verify { customLogger("Custom log message") }
    }
}
