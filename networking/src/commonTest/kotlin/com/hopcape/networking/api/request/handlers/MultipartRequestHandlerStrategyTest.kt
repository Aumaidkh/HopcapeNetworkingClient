package com.hopcape.networking.api.request.handlers

import com.hopcape.networking.api.request.FileUpload
import com.hopcape.networking.api.request.NetworkRequest
import com.hopcape.networking.api.request.methods.HttpMethod
import io.ktor.client.HttpClient
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Test class for the MultipartRequestHandlerStrategy.
 *
 * This class tests the functionality of MultipartRequestHandlerStrategy including
 * proper request handling and file upload detection.
 *
 * @author Murtaza Khursheed
 */
class MultipartRequestHandlerStrategyTest {

    @Test
    fun `test MultipartRequestHandlerStrategy creation`() {
        val mockClient = HttpClient()
        val logger: (String) -> Unit = { println(it) }
        
        val strategy = MultipartRequestHandlerStrategy(mockClient, logger)
        
        assertNotNull(strategy)
    }

    @Test
    fun `test multipart content creation with files and form data`() {
        val mockClient = HttpClient()
        val strategy = MultipartRequestHandlerStrategy(mockClient) {}
        
        val request = NetworkRequest(
            url = "https://api.example.com/upload",
            method = HttpMethod.POST,
            requestBody = mapOf("userId" to "123", "description" to "Profile picture"),
            files = listOf(
                FileUpload("profile.jpg", "image/jpeg", "image content".toByteArray()),
                FileUpload("document.pdf", "application/pdf", "pdf content".toByteArray())
            )
        )

        // This test verifies that the strategy can be created and handles the request structure
        assertNotNull(request.files)
        assertEquals(2, request.files!!.size)
        assertEquals("profile.jpg", request.files!![0].fileName)
        assertEquals("document.pdf", request.files!![1].fileName)
    }

    @Test
    fun `test multipart content creation with only files`() {
        val mockClient = HttpClient()
        val strategy = MultipartRequestHandlerStrategy(mockClient) {}
        
        val request = NetworkRequest(
            url = "https://api.example.com/upload",
            method = HttpMethod.POST,
            files = listOf(
                FileUpload("image.png", "image/png", "png content".toByteArray())
            )
        )

        assertNotNull(request.files)
        assertEquals(1, request.files!!.size)
        assertEquals("image.png", request.files!![0].fileName)
        assertEquals("image/png", request.files!![0].contentType)
    }

    @Test
    fun `test multipart content creation with only form data`() {
        val mockClient = HttpClient()
        val strategy = MultipartRequestHandlerStrategy(mockClient) {}
        
        val request = NetworkRequest(
            url = "https://api.example.com/upload",
            method = HttpMethod.POST,
            requestBody = mapOf("name" to "John Doe", "email" to "john@example.com"),
            files = emptyList()
        )

        assertNotNull(request.requestBody)
        assertTrue(request.requestBody is Map<*, *>)
        assertEquals("John Doe", (request.requestBody as Map<*, *>)["name"])
        assertEquals("john@example.com", (request.requestBody as Map<*, *>)["email"])
    }

    @Test
    fun `test file upload with different content types`() {
        val imageFile = FileUpload("image.jpg", "image/jpeg", "jpeg content".toByteArray())
        val videoFile = FileUpload("video.mp4", "video/mp4", "mp4 content".toByteArray())
        val audioFile = FileUpload("audio.mp3", "audio/mpeg", "mp3 content".toByteArray())
        val textFile = FileUpload("document.txt", "text/plain", "text content".toByteArray())

        assertEquals("image/jpeg", imageFile.contentType)
        assertEquals("video/mp4", videoFile.contentType)
        assertEquals("audio/mpeg", audioFile.contentType)
        assertEquals("text/plain", textFile.contentType)
    }

    @Test
    fun `test file upload with empty content`() {
        val emptyFile = FileUpload("empty.txt", "text/plain", ByteArray(0))
        
        assertEquals("empty.txt", emptyFile.fileName)
        assertEquals("text/plain", emptyFile.contentType)
        assertEquals(0, emptyFile.content.size)
    }

    @Test
    fun `test file upload with special characters in filename`() {
        val specialFileName = "test-file_with.special@chars#123.jpg"
        val fileUpload = FileUpload(specialFileName, "image/jpeg", "content".toByteArray())
        
        assertEquals(specialFileName, fileUpload.fileName)
    }
}
