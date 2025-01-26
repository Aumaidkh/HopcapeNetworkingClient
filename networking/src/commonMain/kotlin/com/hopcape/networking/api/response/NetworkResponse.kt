package com.hopcape.networking.api.response

data class NetworkResponse<T>(
    val code: Int,
    val data: T?,
    val message: String?
)
