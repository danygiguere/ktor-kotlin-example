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

class CarEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CarEntity>(Cars)
    var name by Cars.name
    var year by Cars.year

    fun toModel() = Car(id.value, name, year)
}

@Serializable
data class Car(
    val id: Int,
    val name: String,
    val year: Int
)

@Serializable
data class NewCar(
    val name: String,
    val year: Int
)