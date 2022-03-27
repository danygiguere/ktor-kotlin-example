package com.blog.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class Post(
    val id: Int? = null,
    val userId: Int,
    val title: String,
    val body: String)

object Posts : Table() {
    val id = integer("id").autoIncrement()
    val userId = reference("user_id", Users.id, ReferenceOption.CASCADE)
    val title = varchar("title", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}