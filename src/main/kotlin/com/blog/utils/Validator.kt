package com.blog.utils

import com.blog.plugins.UnprocessableEntityException
import com.blog.rules.MaxLength
import com.blog.rules.MinLength
import com.blog.rules.Required

object Validator {

    fun check(field: String, rules: Array<String>, locale: String, fieldNames: Array<String>): MutableList<String> {
        var array = mutableListOf<String>()
        var fieldName = "fieldName"

        for (name in fieldNames) {
            val fieldNameKeyValue = Utils.splitString(name)
            if(fieldNameKeyValue[0] == locale) {
                fieldName = fieldNameKeyValue[1]
            }
        }

        for (rule in rules) {
            val ruleKeyValue = Utils.splitString(rule)
            val key = ruleKeyValue[0]
            val value = ruleKeyValue[1].toInt()

            array = Required(key, field, value, locale, fieldName, array).check()
            array = MinLength(key, field, value, locale, fieldName, array).check()
            array = MaxLength(key, field, value, locale, fieldName, array).check()
        }
        return array
    }

    fun run(titleArray: MutableList<String>, bodyArray: MutableList<String>) {
        if (titleArray.size > 0 || bodyArray.size > 0) {
            throw UnprocessableEntityException(
                mapOf(
                    "title" to titleArray,
                    "body" to bodyArray
                )
            )
        }
    }
}