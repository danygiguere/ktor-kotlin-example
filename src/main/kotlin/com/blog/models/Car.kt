package com.blog.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object Cars: IntIdTable() {
    val name = varchar("name", 128)
    val year = integer("year")
}

class Car(id: EntityID<Int>, name: String, year: Int): IntEntity(id) {
    companion object : IntEntityClass<Car>(Cars)
    var name by Cars.name
    var year by Cars.year
}