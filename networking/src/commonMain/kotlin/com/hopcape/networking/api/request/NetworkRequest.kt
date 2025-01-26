package com.hopcape.networking.api.request

import com.hopcape.networking.api.request.methods.HttpMethod


data class NetworkRequest(
    val url: String = "",
    val method: HttpMethod = HttpMethod.GET,
    val requestHeaders: Map<String, String>? = null,
    val body: String? = null,
    val params: Map<String, String>? = null,
)
