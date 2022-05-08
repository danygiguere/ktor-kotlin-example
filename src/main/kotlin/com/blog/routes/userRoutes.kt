package com.blog.routes

import com.blog.daos.UserDAO
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.userRoutes() {
    routing {
        val userDAO = UserDAO()

        get("/users") {
            call.respond(mapOf("users" to userDAO.all()))
        }

        get("/users/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(mapOf("users" to userDAO.show(id)))
            call.respondText("This is a text response from userRoutes for user id ${id}")
        }
    }
}