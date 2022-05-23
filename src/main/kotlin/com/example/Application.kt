package com.example

import com.example.config.Database
import com.example.plugins.*
import com.example.seeds.runSeeders
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(CallLogging)
    configureExceptions()
    Database.init()
    configureRouting()
    configureSerialization()
    configureSecurity()
    runSeeders()
}
