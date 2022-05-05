package com.blog.rules

class MaxLength(val key: String, val field: String, val value: Int, val locale: String, val fieldName: String, var array: MutableList<String>) {

    public fun check(): MutableList<String> {
        if(key == "maxLength") {
            if(field.length > value) {
                if(locale === "en") {
                    array.add("The $fieldName field must contain less than $value characters")
                } else {
                    array.add("Le champ $fieldName doit contenir moins de $value caractères")
                }
            }
        }
        return array
    }

}