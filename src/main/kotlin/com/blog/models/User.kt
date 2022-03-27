package com.blog.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class User(
    val id: Int? = null,
    val username: String,
    val email: String,
    val password: String)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 128)
    val email = varchar("email", 128)
    val password = varchar("password", 1024)

    override val primaryKey = PrimaryKey(id)
}