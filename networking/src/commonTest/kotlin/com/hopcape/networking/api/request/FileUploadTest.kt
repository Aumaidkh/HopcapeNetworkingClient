package com.hopcape.networking.api.request

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test class for the FileUpload data class.
 *
 * This class tests the functionality of FileUpload including equality, hashCode,
 * and proper data representation.
 *
 * @author Murtaza Khursheed
 */
class FileUploadTest {

    @Test
    fun `test FileUpload creation with valid data`() {
        val fileName = "test.jpg"
        val contentType = "image/jpeg"
        val content = "test content".toByteArray()

        val fileUpload = FileUpload(fileName, contentType, content)

        assertEquals(fileName, fileUpload.fileName)
        assertEquals(contentType, fileUpload.contentType)
        assertTrue(content.contentEquals(fileUpload.content))
    }

    @Test
    fun `test FileUpload equality with same data`() {
        val content1 = "test content".toByteArray()
        val content2 = "test content".toByteArray()

        val fileUpload1 = FileUpload("test.jpg", "image/jpeg", content1)
        val fileUpload2 = FileUpload("test.jpg", "image/jpeg", content2)

        assertEquals(fileUpload1, fileUpload2)
        assertEquals(fileUpload1.hashCode(), fileUpload2.hashCode())
    }

    @Test
    fun `test FileUpload equality with different data`() {
        val content1 = "test content 1".toByteArray()
        val content2 = "test content 2".toByteArray()

        val fileUpload1 = FileUpload("test1.jpg", "image/jpeg", content1)
        val fileUpload2 = FileUpload("test2.jpg", "image/jpeg", content2)

        assertFalse(fileUpload1 == fileUpload2)
        assertFalse(fileUpload1.hashCode() == fileUpload2.hashCode())
    }

    @Test
    fun `test FileUpload with different content types`() {
        val imageFile = FileUpload("image.jpg", "image/jpeg", "image content".toByteArray())
        val videoFile = FileUpload("video.mp4", "video/mp4", "video content".toByteArray())
        val audioFile = FileUpload("audio.mp3", "audio/mpeg", "audio content".toByteArray())
        val pdfFile = FileUpload("document.pdf", "application/pdf", "pdf content".toByteArray())

        assertEquals("image/jpeg", imageFile.contentType)
        assertEquals("video/mp4", videoFile.contentType)
        assertEquals("audio/mpeg", audioFile.contentType)
        assertEquals("application/pdf", pdfFile.contentType)
    }

    @Test
    fun `test FileUpload with empty content`() {
        val emptyFile = FileUpload("empty.txt", "text/plain", ByteArray(0))

        assertEquals("empty.txt", emptyFile.fileName)
        assertEquals("text/plain", emptyFile.contentType)
        assertEquals(0, emptyFile.content.size)
    }

    @Test
    fun `test FileUpload with special characters in filename`() {
        val fileName = "test-file_with.special@chars#123.jpg"
        val fileUpload = FileUpload(fileName, "image/jpeg", "content".toByteArray())

        assertEquals(fileName, fileUpload.fileName)
    }
}
