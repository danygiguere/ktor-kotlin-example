package com.blog

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.blog.routes.registerRoutes

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ registerRoutes() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}