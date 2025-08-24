# File Upload Support

This document describes the file upload functionality added to the HopcapeNetworking library, which allows you to upload files of any type (images, videos, audio, documents, etc.) along with your network requests.

## Overview

The file upload feature has been designed with backward compatibility in mind. Existing code will continue to work without any changes, while new code can take advantage of file upload capabilities.

## Key Features

- **Support for any file type**: Images, videos, audio, documents, and any other file format
- **Multiple file uploads**: Upload multiple files in a single request
- **Combined with form data**: Mix file uploads with regular form fields
- **Automatic content type detection**: Automatically handles multipart/form-data encoding
- **Backward compatibility**: Existing code continues to work unchanged
- **Cross-platform support**: Works on both Android and iOS

## How It Works

When you add files to a request, the library automatically:
1. Detects that files are present
2. Switches to multipart/form-data encoding
3. Creates the appropriate request handler
4. Sends the request with proper file encoding

## Usage Examples

### Basic File Upload

```kotlin
import com.hopcape.networking.api.NetworkingClient
import com.hopcape.networking.api.Url
import com.hopcape.networking.api.request.FileUpload
import com.hopcape.networking.api.request.methods.HttpMethod

// Upload a single image
val imageFile = FileUpload(
    fileName = "profile.jpg",
    contentType = "image/jpeg",
    content = imageByteArray
)

val result = NetworkingClient.getInstance().makeRequest(UploadResponse::class) {
    setUrl(Url("https://api.example.com/upload"))
    setMethod(HttpMethod.POST)
    setBody(mapOf("userId" to "123"))
    setFiles(listOf(imageFile))
}
```

### Multiple Files with Form Data

```kotlin
val files = listOf(
    FileUpload("profile.jpg", "image/jpeg", profileImageBytes),
    FileUpload("cover.jpg", "image/jpeg", coverImageBytes),
    FileUpload("resume.pdf", "application/pdf", resumeBytes)
)

val result = NetworkingClient.getInstance().makeRequest(UploadResponse::class) {
    setUrl(Url("https://api.example.com/upload/multiple"))
    setMethod(HttpMethod.POST)
    setBody(mapOf(
        "userId" to "123",
        "description" to "Profile update"
    ))
    setFiles(files)
}
```

### Different File Types

```kotlin
// Image upload
val imageFile = FileUpload("photo.png", "image/png", imageBytes)

// Video upload
val videoFile = FileUpload("presentation.mp4", "video/mp4", videoBytes)

// Audio upload
val audioFile = FileUpload("podcast.mp3", "audio/mpeg", audioBytes)

// Document upload
val documentFile = FileUpload("report.pdf", "application/pdf", documentBytes)
```

## FileUpload Class

The `FileUpload` class represents a file to be uploaded:

```kotlin
data class FileUpload(
    val fileName: String,        // Name of the file (including extension)
    val contentType: String,     // MIME type of the file
    val content: ByteArray       // File content as bytes
)
```

### Common Content Types

| File Type | Content Type |
|-----------|--------------|
| JPEG Image | `image/jpeg` |
| PNG Image | `image/png` |
| GIF Image | `image/gif` |
| MP4 Video | `video/mp4` |
| MP3 Audio | `audio/mpeg` |
| PDF Document | `application/pdf` |
| Word Document | `application/vnd.openxmlformats-officedocument.wordprocessingml.document` |
| Excel Spreadsheet | `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` |
| Text File | `text/plain` |

## RequestBuilder Updates

The `RequestBuilder` class now includes a `setFiles()` method:

```kotlin
val request = RequestBuilder()
    .setUrl(Url("https://api.example.com/upload"))
    .setMethod(HttpMethod.POST)
    .setBody(mapOf("key" to "value"))
    .setFiles(listOf(fileUpload1, fileUpload2))
    .build()
```

## NetworkRequest Updates

The `NetworkRequest` class now includes an optional `files` parameter:

```kotlin
data class NetworkRequest(
    val url: String = "",
    val method: HttpMethod = HttpMethod.GET,
    val requestHeaders: Map<String, String>? = null,
    val requestBody: Any? = null,
    val params: Map<String, String>? = null,
    val files: List<FileUpload>? = null  // New parameter
)
```

## Automatic Handler Selection

The library automatically selects the appropriate request handler:

- **Regular requests**: Uses `PostRequestHandlerStrategy` for POST requests without files
- **File uploads**: Uses `MultipartRequestHandlerStrategy` for requests with files
- **GET requests**: Uses `GetRequestHandlingStrategy` (files are ignored for GET requests)

## Backward Compatibility

All existing code continues to work without changes:

```kotlin
// This existing code continues to work exactly as before
val result = NetworkingClient.getInstance().makeRequest(UserResponse::class) {
    setUrl(Url("https://api.example.com/users"))
    setMethod(HttpMethod.POST)
    setBody(mapOf("name" to "John", "email" to "john@example.com"))
}
```

## Error Handling

File uploads use the same error handling pattern as regular requests:

```kotlin
val result = NetworkingClient.getInstance().makeRequest(UploadResponse::class) {
    setUrl(Url("https://api.example.com/upload"))
    setMethod(HttpMethod.POST)
    setFiles(listOf(fileUpload))
}

result.onSuccess { response ->
    println("Upload successful: ${response.message}")
}.onFailure { error ->
    println("Upload failed: ${error.message}")
}
```

## Best Practices

1. **File Size**: Consider file size limits of your API endpoints
2. **Content Types**: Always specify the correct MIME type for your files
3. **File Names**: Use meaningful file names that include extensions
4. **Error Handling**: Implement proper error handling for upload failures
5. **Progress Tracking**: For large files, consider implementing progress tracking
6. **File Validation**: Validate files on the client side before upload

## Platform Considerations

### Android
- Files can be loaded from various sources (gallery, camera, file picker)
- Convert files to ByteArray for upload
- Consider using ContentResolver for large files

### iOS
- Files can be loaded from photo library, camera, or file picker
- Convert files to Data/ByteArray for upload
- Consider using PHAsset for large media files

## Testing

The library includes comprehensive tests for file upload functionality:

- `FileUploadTest`: Tests the FileUpload data class
- `MultipartRequestHandlerStrategyTest`: Tests the multipart request handler
- `RequestBuilderTest`: Tests the updated RequestBuilder with file support

## Examples

See `FileUploadExamples.kt` for comprehensive usage examples covering:
- Single file uploads
- Multiple file uploads
- Different file types
- Form data combination
- Error handling

## Migration Guide

No migration is required for existing code. To add file upload support to existing requests:

1. Add the `setFiles()` call to your RequestBuilder
2. Ensure your API endpoint supports multipart/form-data
3. Handle the response appropriately

## Support

For questions or issues with file upload functionality, please refer to:
- The examples in `FileUploadExamples.kt`
- The test files for implementation details
- The main library documentation
