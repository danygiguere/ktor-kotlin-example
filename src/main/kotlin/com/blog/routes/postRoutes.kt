package com.blog.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.blog.daos.PostDAO
import com.blog.requests.*
import io.ktor.server.auth.*
import io.ktor.server.util.*

fun Application.postRoutes() {
    routing {
        val postDAO = PostDAO()

        get("/posts") {
            call.respond(mapOf("posts" to postDAO.all()))
        }

        get("/posts/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(mapOf("post" to postDAO.show(id)))
        }

        post("/posts") {
            val validatedPost = PostCreateRequest.receive(call)
            val hardcodedUserId = 1 // get the id of the logged in user
            val newPost = postDAO.create(hardcodedUserId, validatedPost.title, validatedPost.body)
            call.respond(mapOf("post" to newPost))
        }

        put("/posts/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val validatedPost = PostUpdateRequest.receive(call)
            val updatedPost = postDAO.update(id, validatedPost.title, validatedPost.body)
            call.respond(mapOf("post" to updatedPost))
        }

        authenticate("auth-jwt") {
            delete("/posts/{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val deletedPost = postDAO.delete(id)
                call.respond(mapOf("post" to deletedPost))
            }
        }
    }
}