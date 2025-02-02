package com.hopcape.networking.api.mocks

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteReadPacket
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.readText

val mockEngine = MockEngine { request ->
    when (request.url.encodedPath) {
        "/get" -> respond("GET response", status = HttpStatusCode.OK)
        "/post" -> respond("POST response", status = HttpStatusCode.Created)
        else -> respond("Not Found", status = HttpStatusCode.NotFound)
    }
}

fun createMockClient(response: String, status: HttpStatusCode = HttpStatusCode.OK): HttpClient {
    val mockEngine = MockEngine { request ->
        respond(
            content = response,
            status = status
        )
    }
    return HttpClient(mockEngine)
}

/**
 * Creates a mock HTTP client for testing purposes in a Kotlin Multiplatform (KMP) project.
 *
 * This function allows you to simulate HTTP responses and optionally validate the request body.
 *
 * @param responseBody The response body to return from the mock server.
 * @param status The HTTP status code to return (default is `HttpStatusCode.OK`).
 * @param headers Optional headers to include in the response.
 * @param validateRequestBody A lambda function to validate the request body. Defaults to no validation.
 * @return A mock [HttpClient] instance.
 */
fun createMockClient(
    responseBody: String,
    status: HttpStatusCode = HttpStatusCode.OK,
    headers: Map<String, List<String>> = emptyMap(),
    validateRequestBody: (String) -> Unit = {}
): HttpClient {
    val mockEngine = MockEngine { request ->
        // Capture and validate the request body if a validator is provided
        val requestBody = request.body.toByteReadPacket().readText()
        validateRequestBody(requestBody)

        // Respond with the predefined response
        respond(
            content = responseBody,
            status = status,
            headers = headersOf(*headers.flatMap { (key, values) ->
                listOf(key to values)
            }.toTypedArray())
        )
    }
    return HttpClient(mockEngine)
}