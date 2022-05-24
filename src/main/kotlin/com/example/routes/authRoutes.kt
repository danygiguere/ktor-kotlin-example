package com.example.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.dsls.UserDSL
import com.example.models.AuthenticatingUser
import com.example.models.NewUser
import com.example.plugins.AuthorizationException
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.authRoutes() {
    val dotenv = Dotenv.load();
    val secret = dotenv.get("JWT_SECRET")
    val issuer = environment?.config?.property("jwt.issuer")?.getString()
    val audience = environment?.config?.property("jwt.audience")?.getString()
    val userDSL = UserDSL()

    post("/register") {
        val newUser = call.receive<NewUser>()
        // todo: validate payload request here
        val hashedPassword = BCrypt.withDefaults().hashToString(12, newUser.password.toCharArray())
        userDSL.create(newUser.username, newUser.email, hashedPassword)

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", newUser.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))
        call.respond(hashMapOf("token" to token))
    }

    post("/login") {
        val authenticatingUser = call.receive<AuthenticatingUser>()
        // todo: validate payload request here
        val dbPassword = userDSL.getHashedPassword(authenticatingUser.email)
        val result: BCrypt.Result = BCrypt.verifyer().verify(authenticatingUser.password.toCharArray(), dbPassword)
        if(!result.verified) {
            throw AuthorizationException("Sorry you are not authorized")
        }

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", authenticatingUser.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))
        call.respond(hashMapOf("token" to token))
    }

    authenticate("auth-jwt") {
        get("/me") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal!!.payload.getClaim("email").asString()
            val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
            call.respondText("Hello, $email! Token is expired at $expiresAt ms.")
        }
    }
}