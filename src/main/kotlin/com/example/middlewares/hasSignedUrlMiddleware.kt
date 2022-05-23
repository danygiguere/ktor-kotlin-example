package com.blog.middlewares

import com.example.plugins.AuthorizationException
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.hasSignedUrlMiddleware(context: ApplicationCall) {
    routing {
        val signature = context.request.queryParameters["signature"]
        if(signature != "1234") {
            throw AuthorizationException("Sorry you are not authorized")
        }
    }
}