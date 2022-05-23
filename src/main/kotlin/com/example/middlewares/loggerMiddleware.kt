package com.blog.middlewares

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.loggerMiddleware() {
    routing {
//        log.info("Hello from loggerMiddleware!")
    }
}