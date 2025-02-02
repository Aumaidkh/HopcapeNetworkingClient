package com.hopcape.networking.api.utils

/**
 * Executes a given API call safely, catching exceptions and returning a [Result].
 *
 * This function wraps the provided API call in a try-catch block to handle exceptions gracefully.
 * It also logs messages using the provided logger function, allowing for customizable logging behavior.
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
 * val result: Result<User> = com.hopcape.networking.api.utils.com.hopcape.networking.api.utils.safeApiCall(::fetchUser) { message -> Log.e("API", message) }
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
        // Execute the API call
        val result = apiCall()

        // Log success message (optional)
        logger("API call succeeded.")

        // Return the successful result
        Result.success(result)
    } catch (e: Exception) {
        // Log the exception message or a default message if the message is null
        val errorMessage = e.message ?: "An unknown error occurred."
        logger("API call failed: $errorMessage")

        // Return the failure result
        Result.failure(e)
    }
}