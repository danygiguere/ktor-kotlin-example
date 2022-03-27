package com.blog.requests

import com.blog.plugins.UnprocessableEntityException
import com.blog.utils.I18n
import com.blog.utils.Validator
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class PostUpdatePayload(
    val title: String,
    val body: String
)

object PostUpdateRequest {

    fun authorize(call: ApplicationCall, locale: String) {
        // throw AuthorizationException if not authorized
    }

    fun validate(payload:PostUpdatePayload, locale: String): PostUpdatePayload {

        val titleArray = Validator.check(payload.title, arrayOf("minLength:2", "maxLength:100"), locale, arrayOf("en:title", "fr:titre"))
        val bodyArray = Validator.check(payload.body, arrayOf("minLength:2", "maxLength:100"), locale, arrayOf("en:body", "fr:contenu"))

        if (titleArray.size > 0 || bodyArray.size > 0) {
            throw UnprocessableEntityException(
                mapOf(
                    "title" to titleArray,
                    "body" to bodyArray
                )
            )
        }
        return payload
    }

    suspend fun receive(call: ApplicationCall): PostUpdatePayload {
        val payload = call.receive<PostUpdatePayload>()
        val locale = I18n.getLocale(call)
        this.authorize(call, locale)
        return this.validate(payload, locale)
    }

}