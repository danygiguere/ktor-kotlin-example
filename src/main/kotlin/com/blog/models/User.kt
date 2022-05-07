package com.blog.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
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

//object Users: IntIdTable() {
//    val username = varchar("username", 128)
//    val email = varchar("email", 128)
//    val password = varchar("password", 1024)
//}
//
//class User(id: EntityID<Int>, username: String, email: String, password: String): IntEntity(id) {
//    companion object : IntEntityClass<User>(Users)
//    var username by Users.username
//    var email by Users.email
//    var password by Users.password
//}