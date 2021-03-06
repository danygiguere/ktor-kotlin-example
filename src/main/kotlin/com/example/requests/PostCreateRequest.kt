package com.example.requests

import com.example.utils.I18n
import com.example.utils.Validator
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class PostCreatePayload(
    val title: String,
    val body: String
)

object PostCreateRequest {

    fun authorize(call: ApplicationCall, locale: String) {
        // throw AuthorizationException if not authorized
    }

    fun validate(payload:PostCreatePayload, locale: String): PostCreatePayload {
        val titleArray = Validator.check(payload.title, arrayOf("minLength:2", "maxLength:100"), locale, arrayOf("en:title", "fr:titre"))
        val bodyArray = Validator.check(payload.body, arrayOf("required", "maxLength:100"), locale, arrayOf("en:body", "fr:contenu"))
        Validator.run(titleArray, bodyArray)
        return payload
    }

    suspend fun receive(call: ApplicationCall): PostCreatePayload {
        val payload = call.receive<PostCreatePayload>()
        val locale = I18n.getLocale(call)
        this.authorize(call, locale)
        return this.validate(payload, locale)
    }

}