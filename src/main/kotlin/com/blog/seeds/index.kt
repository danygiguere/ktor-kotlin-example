package com.blog.seeds

import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*

fun Application.runSeeders() {
    val dotenv = Dotenv.load();
    val env = dotenv.get("ENV")
    if (env == "development") {
        UserSeeder().run()
        PostSeeder().run()
    }
}