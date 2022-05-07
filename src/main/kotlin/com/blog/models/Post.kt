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

//object Posts: IntIdTable() {
//    val user = reference("user", Users)
//    val title = varchar("title", 128)
//    val body = varchar("password", 1024)
//}
//class Post(id: EntityID<Int>,  user: EntityID<Int>, title: String, body: String): IntEntity(id) {
//    companion object : IntEntityClass<Post>(Posts)
//    var user by User referencedOn Posts.user
//    var title by Posts.title
//    var body by Posts.body
//}