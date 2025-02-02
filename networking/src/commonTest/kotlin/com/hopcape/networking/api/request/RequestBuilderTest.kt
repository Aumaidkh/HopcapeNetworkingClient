package com.hopcape.networking.api.request

import com.hopcape.networking.api.Url
import com.hopcape.networking.api.request.methods.HttpMethod
import kotlin.test.Test
import kotlin.test.assertEquals


class RequestBuilderTest {

    @Test
    fun `test setUrl should set URL correctly`() {
        val url = Url("https://api.example.com")
        val request = RequestBuilder().setUrl(url).build()
        assertEquals("https://api.example.com", request.url)
    }

    @Test
    fun `test setMethod should set HTTP method correctly`() {
        val request = RequestBuilder().setMethod(HttpMethod.POST).build()
        assertEquals(HttpMethod.POST, request.method)
    }

    @Test
    fun `test setHeaders should set headers correctly`() {
        val headers = mapOf("Authorization" to "Bearer token")
        val request = RequestBuilder().setHeaders(headers).build()
        assertEquals(headers, request.requestHeaders)
    }

    @Test
    fun `test setBody should set body correctly`() {
        val body = "request body"
        val request = RequestBuilder().setBody(body).build()
        assertEquals(body, request.requestBody)
    }

    @Test
    fun `test build should create a valid NetworkRequest`() {
        val url = Url("https://api.example.com")
        val headers = mapOf("Authorization" to "Bearer token")
        val body = "request body"

        val request = RequestBuilder()
            .setUrl(url)
            .setMethod(HttpMethod.GET)
            .setHeaders(headers)
            .setBody(body)
            .build()

        assertEquals("https://api.example.com", request.url)
        assertEquals(HttpMethod.GET, request.method)
        assertEquals(headers, request.requestHeaders)
        assertEquals(body, request.requestBody)
    }
}
