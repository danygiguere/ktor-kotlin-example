package com.example.plugins

import com.blog.routes.postRoutes
import com.blog.routes.userRoutes
import com.example.routes.authRoutes
import com.example.routes.carRoutes
import com.example.routes.demoRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        demoRoutes()
        authRoutes()
        userRoutes()
        postRoutes()
        carRoutes()
    }
}
