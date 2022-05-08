package com.blog.routes

import com.blog.daos.CarDAO
import com.blog.models.Car
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.carRoutes() {
    routing {
        val carDAO = CarDAO()

//        get("/cars") {
//            call.respond(mapOf("cars" to carDAO.all()))
//        }

//        get("/cars/{id}") {
//            val id = call.parameters.getOrFail<Int>("id").toInt()
//            val car = transaction {
//                Car.findById(id)
//            }
//            call.respond(mapOf("car" to car.toString()))
//        }

        get("/cars/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(mapOf("car" to carDAO.show(id)))
        }

        post("/cars") {
            val car = transaction {
                Car.new {
                    name = "The Last Jedi"
                    year = 2022
                }
            }
            call.respondText("You just created a new car with the id: ${car. id}")
        }
    }
}