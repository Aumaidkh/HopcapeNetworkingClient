package com.hopcape.networking.api.mocks

import kotlinx.serialization.Serializable

@Serializable
data class MockSuccessResponse(
    val data: String
)