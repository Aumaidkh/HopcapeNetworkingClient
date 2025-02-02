package com.hopcape.networking.api.utils

import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests for [safeApiCall] function using Mockito.
 */
class SafeApiCallTest {

    @Test
    fun `safeApiCall should return success when API call succeeds`() = runTest {
        val mockApiCall: suspend () -> String = mock()
        everySuspend {
            mockApiCall()
        } returns "Success Response"

        val result = safeApiCall(mockApiCall)

        assertTrue(result.isSuccess)
        assertEquals("Success Response", result.getOrNull())
    }

    @Test
    fun `safeApiCall should return failure when API call throws exception`() = runTest {
        val mockApiCall: suspend () -> String = mock()
        val exception = RuntimeException("API Failure")
        everySuspend {
            mockApiCall()
        } throws exception

        val result = safeApiCall(mockApiCall)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `safeApiCall should log error message when exception occurs`() = runTest {
        val mockApiCall: suspend () -> String = mock()
        val exception = RuntimeException("API Failure")
        everySuspend {
            mockApiCall()
        } throws exception

        val mockLogger: (String) -> Unit = mock()
        every {
            mockLogger(any())
        } returns Unit

        safeApiCall(mockApiCall, mockLogger)
        verify{mockLogger.invoke("API call failed: ${exception.message}")}
    }
}