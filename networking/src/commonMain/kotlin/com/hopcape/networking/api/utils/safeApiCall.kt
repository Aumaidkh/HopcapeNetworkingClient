package com.hopcape.networking.api.utils

/**
 * Executes a given API call safely, catching exceptions and returning a [Result].
 *
 * @param T The type of data expected from the API call.
 * @param apiCall A suspending function representing the API request.
 * @param logger An optional logging function for error messages. Defaults to an empty logger.
 * @return A [Result] object containing either the success value or the caught exception.
 *
 * ##### Example Usage:
 * ```kotlin
 * suspend fun fetchUser(): User {
 *     return apiService.getUser()
 * }
 *
 * val result: Result<User> = com.hopcape.networking.api.utils.safeApiCall(::fetchUser) { message -> Log.e("API", message) }
 * result.onSuccess { user -> println("User: $user") }
 * result.onFailure { error -> println("Error: ${error.message}") }
 * ```
 *
 * @author Murtaza Khursheed
 */
internal suspend fun <T> safeApiCall(
    apiCall: suspend () -> T,
    logger: (String) -> Unit = {}
): Result<T> {
    return try {
        Result.success(apiCall())
    } catch (e: Exception) {
        logger(e.message.toString())
        Result.failure(e)
    }
}