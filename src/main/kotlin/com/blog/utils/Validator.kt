package com.blog.utils

object Validator {

    fun check(field: String, rules: Array<String>, locale: String, fieldNames: Array<String>): MutableList<String> {
        val array = mutableListOf<String>()
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

            if(key == "required") {
                if(field.isEmpty()) {
                    if (locale === "en") {
                        array.add("The $fieldName field is required")
                    } else {
                        array.add("Le champ $fieldName est requis")
                    }
                }
            }
            if(key == "minLength") {
                if(field.length < value) {
                    if(locale === "en") {
                        array.add("The $fieldName field must contain at least $value characters")
                    } else {
                        array.add("Le champ $fieldName doit contenir au moins $value caractères")
                    }
                }
            }
            if(key == "maxLength") {
                if(field.length > value) {
                    if(locale === "en") {
                        array.add("The $fieldName field must contain less than $value characters")
                    } else {
                        array.add("Le champ $fieldName doit contenir moins de $value caractères")
                    }
                }
            }
        }
        return array
    }
}