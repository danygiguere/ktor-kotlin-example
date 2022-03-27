package com.blog.routes

import com.blog.middlewares.hasSignedUrlMiddleware
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.demoRoutes() {
    routing {
        get("/") {
            call.respondRedirect("demo")
        }

        get("/demo") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/demo/text-response") {
            call.respondText("This is a text response from demoRoutes@/demo/text-response")
        }

        get("/demo/signed-url") {
            // you must add the url param ?signature=1234 for this route to respond successfully
            hasSignedUrlMiddleware(context as ApplicationCallWithContext)
            call.respondText("This is a text response from demoRoutes@/demo/signed-url")
        }

        get("/demo/translation") {
            call.respondText("This is a text response from demoRoutes@/demo/translation")
        }

        get("/demo/logger") {
            log.info("Hello from demoRoutes@/demo/logger")
            log.info(call.request.httpMethod.value)
            log.info(call.request.headers["User-Agent"])
            log.info(call.request.uri)
            log.info(call.request.host())
            log.info(call.request.path())
            log.info(call.request.path().startsWith("/demo/logger").toString())
            call.respondText("This is a text response from demoRoutes@/demo/logger")
        }

        get("/demo/logger/{id}") {
            log.info(call.parameters["id"]) // /demo/logger/1
            log.info(call.request.queryParameters["name"]) // ?name=john
            call.respondText("This is a text response from demoRoutes@/demo/logger")
        }
    }
}