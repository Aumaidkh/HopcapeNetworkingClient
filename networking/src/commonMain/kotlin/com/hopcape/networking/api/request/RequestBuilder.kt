package com.hopcape.networking.api.request

import com.hopcape.networking.api.Url

class RequestBuilder {
    private var request: NetworkRequest = NetworkRequest()

    fun setUrl(url: Url): NetworkRequest {
        request = request.copy(url = url.value)
        return request

    }
    fun setMethod(method: HttpMethod): NetworkRequest {
        request = request.copy(method = method)
        return request
    }
    fun setHeaders(headers: Map<String, String>): NetworkRequest {
        request = request.copy(requestHeaders = headers)
        return request
    }
    fun setBody(body: String): NetworkRequest {
        request = request.copy(body = body)
        return request
    }
    fun build() =
        request
}