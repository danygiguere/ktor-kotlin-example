package com.blog.dsls

import com.blog.config.Database.dbQuery
import com.blog.models.*
import org.jetbrains.exposed.sql.*

class UserDSL {
    suspend fun all(): List<User> = dbQuery {
        Users.selectAll().map(Users::resultRowToUser)
    }

    suspend fun allWithPosts(): List<User> = dbQuery {
        (Users innerJoin Posts)
            .slice(Users.id, Users.email, Users.username, Users.password, Posts.id, Posts.userId, Posts.title, Posts.body)
            .selectAll().map(Users::resultRowToUser)
    }

    suspend fun show(id: Int): User? = dbQuery {
        Users
            .select { Users.id eq id }
            .map(Users::resultRowToUser)
            .singleOrNull()
    }

    suspend fun showWithPosts(id: Int): UserWithPosts? = dbQuery {
        (Users innerJoin Posts)
            .slice(Users.id, Users.email, Users.username, Users.password, Posts.id, Posts.userId, Posts.title, Posts.body)
            .select { Users.id eq id }
            .map(Users::resultRowToUserWithPosts)
            .singleOrNull()
    }

    suspend fun create(username: String, email: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(Users::resultRowToUser)
    }

    suspend fun update(id: Int, username: String, email: String, password: String): Boolean = dbQuery {
        Users.update({ Users.id eq id }) {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.password] = password
        } > 0
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}