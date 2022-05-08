package com.blog.daos

import com.blog.config.Database.dbQuery
import com.blog.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

//@Serializable
class SerializedCar(id: EntityID<Int>, name: String, year: Int): IntEntity(id)

class CarDAO {
    private fun resultRowToCar(row: ResultRow) = SerializedCar(
        id = row[Cars.id],
        name = row[Cars.name],
        year = row[Cars.year]
    )

    suspend fun show(id: Int): SerializedCar? = transaction {
        Cars
            .select { Cars.id eq id }
            .map(::resultRowToCar)
            .singleOrNull()
    }
}