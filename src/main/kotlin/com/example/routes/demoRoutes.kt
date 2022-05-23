package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.demoRoutes() {

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
//            hasSignedUrlMiddleware(context as ApplicationCall)
        call.respondText("This is a text response from demoRoutes@/demo/signed-url")
    }

    get("/demo/translation") {
        call.respondText("This is a text response from demoRoutes@/demo/translation")
    }

    get("/demo/logger") {
        call.application.environment.log.info("Hello from demoRoutes@/demo/logger")
        call.application.environment.log.info(call.request.httpMethod.value)
        call.application.environment.log.info(call.request.headers["User-Agent"])
        call.application.environment.log.info(call.request.uri)
        call.application.environment.log.info(call.request.host())
        call.application.environment.log.info(call.request.path())
        call.application.environment.log.info(call.request.path().startsWith("/demo/logger").toString())
        call.respondText("This is a text response from demoRoutes@/demo/logger")
    }

    get("/demo/logger/{id}") {
        call.application.environment.log.info(call.parameters["id"])
        call.application.environment.log.info(call.parameters["name"]) //?name=john
        call.respondText("This is a text response from demoRoutes@/demo/logger")
    }

    get("/demo/exposed") {
        call.respondText("This is a text response from demoRoutes@/exposed")
    }

}