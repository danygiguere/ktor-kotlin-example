package com.blog.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.blog.dsls.PostDSL
import com.blog.requests.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.util.*

fun Application.postRoutes() {
    routing {
        val postDSL = PostDSL()

        get("/posts") {
            call.respond(postDSL.all())
        }

        get("/posts/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val post = postDSL.show(id)
            if (post != null) {
                call.respond(post)
            } else {
                call.respondText("Post not found", status = HttpStatusCode.OK)
            }
        }

        post("/posts") {
            val validatedPost = PostCreateRequest.receive(call)
            val hardcodedUserId = 1 // get the id of the logged in user
            val newPost = postDSL.create(hardcodedUserId, validatedPost.title, validatedPost.body)
            if (newPost != null) {
                call.respond(newPost)
            } else {
                call.respondText("Post not found", status = HttpStatusCode.OK)
            }
        }

        put("/posts/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val validatedPost = PostUpdateRequest.receive(call)
            call.respond(postDSL.update(id, validatedPost.title, validatedPost.body))
        }

        authenticate("auth-jwt") {
            delete("/posts/{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(postDSL.delete(id))
            }
        }
    }
}