package com.blog.rules

class Required(val key: String, val field: String, val value: Int, val locale: String, val fieldName: String, var array: MutableList<String>) {

    public fun check(): MutableList<String> {
        if(key == "required") {
            if(field.isEmpty()) {
                if (locale === "en") {
                    array.add("The $fieldName field is required")
                } else {
                    array.add("Le champ $fieldName est requis")
                }
            }
        }
        return array
    }

}