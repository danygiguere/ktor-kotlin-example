package com.blog.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.registerRoutes() {
    routing {
        demoRoutes()
        authRoutes()
        userRoutes()
        postRoutes()
        carRoutes()
    }
}