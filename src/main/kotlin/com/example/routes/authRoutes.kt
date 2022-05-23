package com.example.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.dsls.UserDSL
import com.example.models.AuthenticatingUser
import com.example.models.Users
import com.example.plugins.AuthorizationException
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import java.util.*




fun Route.authRoutes() {
    val dotenv = Dotenv.load();
    val secret = dotenv.get("JWT_SECRET")
    val issuer = environment?.config?.property("jwt.issuer")?.getString()
    val audience = environment?.config?.property("jwt.audience")?.getString()
    val userDSL = UserDSL()

//    post("/register") {
//        val newUser = call.receive<NewUser>()
//        val hashedPassword = BCrypt.withDefaults().hash(12, newUser.password.toByteArray())
//        val dbUser = userDSL.create(newUser.username, newUser.email, hashedPassword)
//
//        Users.insert {
//            it[username] = newUser.username
//            it[email] = newUser.email
//            it[password] = hashedPassword
//        }
//
//        val token = JWT.create()
//            .withAudience(audience)
//            .withIssuer(issuer)
//            .withClaim("email", dbUser?.email)
//            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
//            .sign(Algorithm.HMAC256(secret))
//        call.respond(hashMapOf("token" to token))
//    }

    post("/login") {
        val authenticatingUser = call.receive<AuthenticatingUser>()
        val dbUser = userDSL.findUserByEmail(authenticatingUser.email)
//        val hashedPassword = BCrypt.withDefaults().hash(12, authenticatingUser.password.toByteArray())
//        val result: BCrypt.Result = BCrypt.verifyer().verify(dbUser?.password?.toByteArray(), hashedPassword)
//        if(!result.verified) {
//            throw AuthorizationException("Sorry you are not authorized")
//        }
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", dbUser?.email)
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