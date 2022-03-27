package com.blog

import com.blog.config.Database
import com.blog.plugins.*
import com.blog.routes.registerRoutes
import com.blog.seeds.runSeeders
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureExceptions()
    Database.init()
    configureSerialization()
    configureJWT()
    registerRoutes()
    runSeeders()
}
