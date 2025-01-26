package com.hopcape.networking.api.request.methods

/**
 * Enum class representing different HTTP methods used for making requests.
 *
 * This class includes standard HTTP methods such as:
 * - `GET`: Used for retrieving data from the server.
 * - `POST`: Used for sending data to the server.
 * - `PUT`: Used for updating data on the server.
 * - `PATCH`: Used for partial updates to data on the server.
 * - `DELETE`: Used for deleting data from the server.
 *
 * ## Example Usage:
 * ```kotlin
 * val method: com.hopcape.networking.api.request.methods.HttpMethod = com.hopcape.networking.api.request.methods.HttpMethod.GET
 * ```
 *
 * @author Murtaza Khursheed
 */
enum class HttpMethod {
    GET, POST, PUT, PATCH, DELETE
}
