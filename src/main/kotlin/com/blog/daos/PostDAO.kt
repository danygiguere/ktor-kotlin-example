package com.blog.daos

import com.blog.config.Database.dbQuery
import com.blog.models.*
import org.jetbrains.exposed.sql.*

class PostDAO  {
    private fun resultRowToPost(row: ResultRow) = Post(
        id = row[Posts.id],
        user = row[Posts.user],
        title = row[Posts.title],
        body = row[Posts.body]
    )

    suspend fun all(): List<Post> = dbQuery {
        Posts.selectAll().map(::resultRowToPost)
    }

    suspend fun show(id: Int): Post? = dbQuery {
        Posts
            .select { Posts.id eq id }
            .map(::resultRowToPost)
            .singleOrNull()
    }

    suspend fun create(user: Int, title: String, body: String): Post? = dbQuery {
        val insertStatement = Posts.insert {
            it[Posts.user] = user
            it[Posts.title] = title
            it[Posts.body] = body
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPost)
    }

    suspend fun update(id: Int, title: String, body: String): Boolean = dbQuery {
        Posts.update({ Posts.id eq id }) {
            it[Posts.title] = title
            it[Posts.body] = body
        } > 0
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        Posts.deleteWhere { Posts.id eq id } > 0
    }
}