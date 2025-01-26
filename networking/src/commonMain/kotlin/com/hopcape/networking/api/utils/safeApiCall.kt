package com.hopcape.networking.api.utils

internal suspend fun <T> safeApiCall(
    apiCall: suspend () -> T,
    logger: (String) -> Unit = {}
): Result<T> {
    return try {
        logger("Success: API call successful")
        // Run the API call within a safe scope and return the success result
        Result.success(apiCall())
    } catch (e: Exception) {
        // Catch any exceptions and return them as failure result
        logger(e.message.toString())
        Result.failure(e)
    }
}