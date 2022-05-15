package com.blog.routes

import com.blog.dsls.UserDSL
import io.ktor.server.application.*
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
            call.respond(userDSL.show(id))
        }

        get("/users/{id}/posts") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(userDSL.showWithPosts(id))
        }
    }
}