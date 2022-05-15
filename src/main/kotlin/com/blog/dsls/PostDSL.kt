package com.blog.dsls

import com.blog.config.Database.dbQuery
import com.blog.models.*
import org.jetbrains.exposed.sql.*

class PostDSL  {
    suspend fun all(): List<Post> = dbQuery {
        Posts.selectAll().map(Posts::resultRowToPost)
    }

    suspend fun show(id: Int): Post? = dbQuery {
        Posts
            .select { Posts.user_id eq id }
            .map(Posts::resultRowToPost)
            .singleOrNull()
    }

    suspend fun create(user_id: Int, title: String, body: String): Post? = dbQuery {
        val insertStatement = Posts.insert {
            it[Posts.user_id] = user_id
            it[Posts.title] = title
            it[Posts.body] = body
        }
        insertStatement.resultedValues?.singleOrNull()?.let(Posts::resultRowToPost)
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