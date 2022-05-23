package com.example.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 128)
    val email = varchar("email", 128)
    val password = varchar("password", 1024)

    override val primaryKey = PrimaryKey(id)

    fun resultRowToUser(row: ResultRow) = User(
        id = row[this.id],
        username = row[this.username],
        email = row[this.email]
    )

    fun getPassword(row: ResultRow) = row[this.password]
}

@Serializable
data class AuthenticatingUser(
    val email: String,
    val password: String
)

@Serializable
data class NewUser(
    val id: Int? = null,
    val username: String,
    val email: String,
    val password: String
)

@Serializable
data class User(
    val id: Int? = null,
    val username: String,
    val email: String
)

@Serializable
data class UserWithPosts(
    val id: Int? = null,
    val username: String,
    val email: String,
    val posts: List<Post>? = null
)