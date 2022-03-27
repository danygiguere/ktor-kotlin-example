package com.blog.utils

import io.ktor.server.application.*
import io.ktor.server.request.*

object I18n {

    suspend fun getLocale(call: ApplicationCall): String {
        var locale: String? = call.request.acceptLanguage() ?: "en"
        if(locale != "fr") {
            locale = "en"
        }
        return locale
    }

}