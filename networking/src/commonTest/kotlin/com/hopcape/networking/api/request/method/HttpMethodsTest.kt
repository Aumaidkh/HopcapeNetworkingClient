package com.hopcape.networking.api.request.method

import com.hopcape.networking.api.request.methods.HttpMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class HttpMethodTest {

    @Test
    fun `test HttpMethod GET`() {
        val method = HttpMethod.GET
        assertEquals(HttpMethod.GET, method)
    }

    @Test
    fun `test HttpMethod POST`() {
        val method = HttpMethod.POST
        assertEquals(HttpMethod.POST, method)
    }

    @Test
    fun `test HttpMethod PUT`() {
        val method = HttpMethod.PUT
        assertEquals(HttpMethod.PUT, method)
    }

    @Test
    fun `test HttpMethod PATCH`() {
        val method = HttpMethod.PATCH
        assertEquals(HttpMethod.PATCH, method)
    }

    @Test
    fun `test HttpMethod DELETE`() {
        val method = HttpMethod.DELETE
        assertEquals(HttpMethod.DELETE, method)
    }

    @Test
    fun `test HttpMethod values`() {
        val values = HttpMethod.values()
        assertEquals(5, values.size)
        assertTrue(values.contains(HttpMethod.GET))
        assertTrue(values.contains(HttpMethod.POST))
        assertTrue(values.contains(HttpMethod.PUT))
        assertTrue(values.contains(HttpMethod.PATCH))
        assertTrue(values.contains(HttpMethod.DELETE))
    }
}
