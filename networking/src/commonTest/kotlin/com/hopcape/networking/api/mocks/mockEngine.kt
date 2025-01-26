package com.hopcape.networking.api.mocks

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode

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