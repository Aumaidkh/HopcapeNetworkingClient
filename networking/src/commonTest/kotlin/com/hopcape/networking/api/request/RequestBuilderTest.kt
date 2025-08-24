package com.hopcape.networking.api.request

import com.hopcape.networking.api.Url
import com.hopcape.networking.api.request.methods.HttpMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Test class for the RequestBuilder.
 *
 * This class tests the functionality of RequestBuilder including building requests
 * with various configurations and the new file upload support.
 *
 * @author Murtaza Khursheed
 */
class RequestBuilderTest {

    @Test
    fun `test RequestBuilder with basic configuration`() {
        val request = RequestBuilder()
            .setUrl(Url("https://api.example.com"))
            .setMethod(HttpMethod.GET)
            .build()

        assertEquals("https://api.example.com", request.url)
        assertEquals(HttpMethod.GET, request.method)
    }

    @Test
    fun `test RequestBuilder with headers and body`() {
        val headers = mapOf("Authorization" to "Bearer token")
        val body = mapOf("key" to "value")

        val request = RequestBuilder()
            .setUrl(Url("https://api.example.com"))
            .setMethod(HttpMethod.POST)
            .setHeaders(headers)
            .setBody(body)
            .build()

        assertEquals("https://api.example.com", request.url)
        assertEquals(HttpMethod.POST, request.method)
        assertEquals(headers, request.requestHeaders)
        assertEquals(body, request.requestBody)
    }

    @Test
    fun `test RequestBuilder with files`() {
        val files = listOf(
            FileUpload("image.jpg", "image/jpeg", "image content".toByteArray()),
            FileUpload("document.pdf", "application/pdf", "pdf content".toByteArray())
        )

        val request = RequestBuilder()
            .setUrl(Url("https://api.example.com/upload"))
            .setMethod(HttpMethod.POST)
            .setFiles(files)
            .build()

        assertEquals("https://api.example.com/upload", request.url)
        assertEquals(HttpMethod.POST, request.method)
        assertNotNull(request.files)
        assertEquals(2, request.files!!.size)
        assertEquals("image.jpg", request.files!![0].fileName)
        assertEquals("document.pdf", request.files!![1].fileName)
    }

    @Test
    fun `test RequestBuilder with complete configuration`() {
        val url = "https://api.example.com/upload"
        val method = HttpMethod.POST
        val headers = mapOf("Authorization" to "Bearer token")
        val body = mapOf("userId" to "123", "description" to "Profile picture")
        val params = mapOf("category" to "profile")
        val files = listOf(
            FileUpload("profile.jpg", "image/jpeg", "image content".toByteArray())
        )

        val request = RequestBuilder()
            .setUrl(Url(url))
            .setMethod(method)
            .setHeaders(headers)
            .setBody(body)
            .setParams(params)
            .setFiles(files)
            .build()

        assertEquals(url, request.url)
        assertEquals(method, request.method)
        assertEquals(headers, request.requestHeaders)
        assertEquals(body, request.requestBody)
        assertEquals(params, request.params)
        assertNotNull(request.files)
        assertEquals(1, request.files!!.size)
        assertEquals("profile.jpg", request.files!![0].fileName)
    }

    @Test
    fun `test RequestBuilder chaining`() {
        val request = RequestBuilder()
            .setUrl(Url("https://api.example.com"))
            .setMethod(HttpMethod.POST)
            .setHeaders(mapOf("Content-Type" to "application/json"))
            .setBody("test body")
            .setFiles(listOf(
                FileUpload("test.txt", "text/plain", "test content".toByteArray())
            ))
            .build()

        assertEquals("https://api.example.com", request.url)
        assertEquals(HttpMethod.POST, request.method)
        assertEquals("test body", request.requestBody)
        assertNotNull(request.files)
        assertEquals(1, request.files!!.size)
    }

    @Test
    fun `test RequestBuilder with empty files list`() {
        val request = RequestBuilder()
            .setUrl(Url("https://api.example.com"))
            .setMethod(HttpMethod.POST)
            .setFiles(emptyList())
            .build()

        assertNotNull(request.files)
        assertEquals(0, request.files!!.size)
    }

    @Test
    fun `test RequestBuilder with different file types`() {
        val files = listOf(
            FileUpload("image.png", "image/png", "png content".toByteArray()),
            FileUpload("video.mp4", "video/mp4", "video content".toByteArray()),
            FileUpload("audio.mp3", "audio/mpeg", "audio content".toByteArray())
        )

        val request = RequestBuilder()
            .setUrl(Url("https://api.example.com/upload"))
            .setMethod(HttpMethod.POST)
            .setFiles(files)
            .build()

        assertNotNull(request.files)
        assertEquals(3, request.files!!.size)
        assertEquals("image/png", request.files!![0].contentType)
        assertEquals("video/mp4", request.files!![1].contentType)
        assertEquals("audio/mpeg", request.files!![2].contentType)
    }
}
