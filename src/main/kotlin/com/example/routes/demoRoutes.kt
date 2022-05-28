package com.example.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.models.Posts
import com.example.models.Users

import com.example.plugins.AuthorizationException
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

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

    get("/demo/signed-url") {
        // you must add the url param ?signature=$2a$12$DvbEVyvGzearyPn7.c0QQO1edR2R7zyo68Aso/qIntA3KI1mMCe82 for this route to respond successfully
        // $2a$12$DvbEVyvGzearyPn7.c0QQO1edR2R7zyo68Aso/qIntA3KI1mMCe82 is the hashed route /demo/signed-url
        // I need to replace the hash with an encryption because the hash is not url friendly (and ugly)
        val signature = context.request.queryParameters["signature"]
        val result: BCrypt.Result = BCrypt.verifyer().verify("/demo/signed-url".toCharArray(), signature)
        if(!result.verified) {
            throw AuthorizationException("Sorry you are not authorized")
        }
        call.respondText("This is a text response from demoRoutes@/demo/signed-url")
    }

}