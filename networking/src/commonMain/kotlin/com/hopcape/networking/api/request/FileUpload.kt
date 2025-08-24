package com.hopcape.networking.api.request

/**
 * Data class representing a file to be uploaded in a network request.
 *
 * This class encapsulates file data including the file name, content type, and the actual file content.
 * It supports various file types including images, videos, audio, documents, and any other file format.
 *
 * ## Key Features:
 * - Supports any file type through the content type parameter
 * - Maintains the original file name for proper file handling on the server
 * - Flexible content representation that can work across different platforms
 *
 * ## Example Usage:
 * ```kotlin
 * val imageFile = FileUpload(
 *     fileName = "profile.jpg",
 *     contentType = "image/jpeg",
 *     content = imageByteArray
 * )
 * 
 * val documentFile = FileUpload(
 *     fileName = "document.pdf",
 *     contentType = "application/pdf",
 *     content = pdfByteArray
 * )
 * ```
 *
 * @author Murtaza Khursheed
 */
data class FileUpload(
    /**
     * The name of the file to be uploaded.
     *
     * This should include the file extension and be meaningful to identify the file.
     * The server will use this name when saving the uploaded file.
     *
     * ### Example:
     * ```kotlin
     * val fileName = "profile_picture.jpg"
     * ```
     */
    val fileName: String,

    /**
     * The MIME type of the file content.
     *
     * This specifies the type of file being uploaded, which helps the server
     * understand how to process and store the file.
     *
     * ### Common Examples:
     * - `image/jpeg` for JPEG images
     * - `image/png` for PNG images
     * - `video/mp4` for MP4 videos
     * - `audio/mpeg` for MP3 audio files
     * - `application/pdf` for PDF documents
     * - `text/plain` for text files
     */
    val contentType: String,

    /**
     * The actual file content as a byte array.
     *
     * This contains the binary data of the file to be uploaded.
     * The content should match the specified content type.
     */
    val content: ByteArray
) {
    /**
     * Custom equals implementation to compare FileUpload objects by their content.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FileUpload) return false

        if (fileName != other.fileName) return false
        if (contentType != other.contentType) return false
        if (!content.contentEquals(other.content)) return false

        return true
    }

    /**
     * Custom hashCode implementation based on the object's properties.
     */
    override fun hashCode(): Int {
        var result = fileName.hashCode()
        result = 31 * result + contentType.hashCode()
        result = 31 * result + content.contentHashCode()
        return result
    }
}
