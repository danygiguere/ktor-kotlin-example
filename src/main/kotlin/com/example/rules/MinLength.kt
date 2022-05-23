package com.example.rules

class MinLength(val key: String, val field: String, val value: Int, val locale: String, val fieldName: String, var array: MutableList<String>) {

    public fun check(): MutableList<String> {
        if(key == "minLength") {
            if(field.length < value) {
                if(locale === "en") {
                    array.add("The $fieldName field must contain at least $value characters")
                } else {
                    array.add("Le champ $fieldName doit contenir au moins $value caractères")
                }
            }
        }
        return array
    }

}