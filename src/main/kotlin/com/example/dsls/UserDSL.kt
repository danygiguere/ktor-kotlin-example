package com.example.dsls

import com.example.config.Database.dbQuery
import com.example.models.*
import org.jetbrains.exposed.sql.*

class UserDSL {
    suspend fun all(): List<User> = dbQuery {
        Users.selectAll().map(Users::resultRowToUser)
    }

    suspend fun show(id: Int): User? = dbQuery {
        Users
            .select { Users.id eq id }
            .map(Users::resultRowToUser)
            .singleOrNull()
    }

    suspend fun showWithPosts(id: Int): UserWithPosts? = dbQuery {
        val user = Users
            .select { Users.id eq id }
            .map(Users::resultRowToUser)
            .singleOrNull()

        val posts = Posts
            .select { Posts.user_id eq id }
            .map(Posts::resultRowToPost)

        user?.let { UserWithPosts(user.id, user.username, user.email, posts) }
    }

    suspend fun create(username: String, email: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(Users::resultRowToUser)
    }

    suspend fun update(id: Int, username: String, email: String): Boolean = dbQuery {
        Users.update({ Users.id eq id }) {
            it[Users.username] = username
            it[Users.email] = email
        } > 0
    }

    //todo updatePassword function that takes password and password_confirmation parameters

    suspend fun delete(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    suspend fun getHashedPassword(email: String): String? = dbQuery {
        Users
            .select { Users.email eq email }
            .map(Users::getHashedPassword)
            .singleOrNull()
    }
}