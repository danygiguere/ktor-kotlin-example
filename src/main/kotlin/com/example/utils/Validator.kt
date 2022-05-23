package com.example.utils

import com.example.rules.MaxLength
import com.example.rules.MinLength
import com.example.rules.Required
import com.example.plugins.UnprocessableEntityException

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