package com.example.plugins

import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureExceptions() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if(cause is AuthorizationException) {
                call.respondText(text = "403: $cause", status = HttpStatusCode.Forbidden)
            } else if(cause is UnprocessableEntityException) {
                call.respond(HttpStatusCode.UnprocessableEntity, mapOf("errors" to cause.errors))
            } else {
//                log.error("$cause")
                call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}

class AuthorizationException(override val message: String?) : Throwable()
class UnprocessableEntityException(val errors: Map<String, List<String>>) : Throwable(message = null)