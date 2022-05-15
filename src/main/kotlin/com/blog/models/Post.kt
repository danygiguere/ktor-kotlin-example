package com.blog.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

object Posts : Table() {
    val id = integer("id").autoIncrement()
    val user_id = (integer("user_id") references Users.id)
    val title = varchar("title", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)

    fun resultRowToPost(row: ResultRow) = Post(
        id = row[this.id],
        userId = row[this.user_id],
        title = row[this.title],
        body = row[this.body]
    )
}

@Serializable
data class Post(
    val id: Int? = null,
    val userId: Int,
    val title: String,
    val body: String)