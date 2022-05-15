package com.blog.routes

import com.blog.dsls.UserDSL
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.userRoutes() {
    routing {
        val userDSL = UserDSL()

        get("/users") {
            call.respond(userDSL.all())
        }

        get("/users/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val user = userDSL.show(id)
            call.respond(mapOf("user" to user))
        }

        get("/users/{id}/posts") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val user = userDSL.showWithPosts(id)
            call.respond(mapOf("user" to user))
        }
    }
}