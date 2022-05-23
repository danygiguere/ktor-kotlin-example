package com.example.utils

object Utils {

    fun splitString(str: String): List<String> {
        val hasDelimiter : Boolean = ":" in str
        return if(hasDelimiter) {
            str.split(":")
        } else {
            listOf(str, "0")
        }
    }

}