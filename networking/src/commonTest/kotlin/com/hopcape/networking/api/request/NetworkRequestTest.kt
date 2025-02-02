package com.hopcape.networking.api.request

import com.hopcape.networking.api.request.methods.HttpMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NetworkRequestTest {

    @Test
    fun `test default constructor values`() {
        val request = NetworkRequest()
        assertEquals("", request.url)
        assertEquals(HttpMethod.GET, request.method)
        assertNull(request.requestHeaders)
        assertNull(request.requestBody)
        assertNull(request.params)
    }

    @Test
    fun `test constructor with parameters`() {
        val request = NetworkRequest(
            url = "https://api.example.com",
            method = HttpMethod.POST,
            requestHeaders = mapOf("Authorization" to "Bearer token"),
            requestBody = "request body",
            params = mapOf("key" to "value")
        )

        assertEquals("https://api.example.com", request.url)
        assertEquals(HttpMethod.POST, request.method)
        assertEquals(mapOf("Authorization" to "Bearer token"), request.requestHeaders)
        assertEquals("request body", request.requestBody)
        assertEquals(mapOf("key" to "value"), request.params)
    }

    @Test
    fun `test method default value`() {
        val request = NetworkRequest(url = "https://api.example.com")
        assertEquals(HttpMethod.GET, request.method)
    }

    @Test
    fun `test overriding the HTTP method`() {
        val request = NetworkRequest(
            url = "https://api.example.com",
            method = HttpMethod.POST
        )
        assertEquals(HttpMethod.POST, request.method)
    }
}
