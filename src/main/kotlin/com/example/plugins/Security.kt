package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*

fun Application.configureSecurity() {

    val dotenv = Dotenv.load();
    val secret = dotenv.get("JWT_SECRET")
    val issuer = this@configureSecurity.environment.config.property("jwt.issuer").getString()
    val jwtAudience = this@configureSecurity.environment.config.property("jwt.audience").getString()
    val myRealm = this@configureSecurity.environment.config.property("jwt.realm").getString()
    
    authentication {
        jwt("auth-jwt") {
                realm = myRealm
                verifier(
                    JWT
                        .require(Algorithm.HMAC256(secret))
                        .withAudience(jwtAudience)
                        .withIssuer(issuer)
                        .build()
                )
                validate { credential ->
                    if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
                }
            }
        }

}
