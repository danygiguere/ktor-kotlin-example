package com.example.daos

import com.example.config.Database.dbQuery
import com.example.models.*

class CarDAO {

    suspend fun all(): Iterable<Car> = dbQuery {
        CarEntity.all().map(CarEntity::toModel)
    }

    suspend fun show(id: Int) = dbQuery {
        CarEntity[id].toModel()
    }

    suspend fun create(car: NewCar) = dbQuery {
       CarEntity.new {
            this.name = car.name
            this.year = car.year
        }.toModel()
    }

    suspend fun update(id: Int, car: NewCar) = dbQuery {
        CarEntity[id].name = car.name
        CarEntity[id].year = car.year
        CarEntity[id].toModel()
    }

    suspend fun delete(id: Int) = dbQuery {
        CarEntity[id].delete()
    }

}