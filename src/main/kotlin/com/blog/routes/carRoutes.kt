package com.blog.routes

import com.blog.daos.CarDAO
import com.blog.models.NewCar
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.carRoutes() {
    routing {
        val carDAO = CarDAO()

        get("/cars") {
            call.respond(carDAO.all())
        }

        get("/cars/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(carDAO.show(id))
        }

        post("/cars") {
            val car = call.receive<NewCar>()
            call.respond(carDAO.create(car))
        }

        put("cars/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val car = call.receive<NewCar>()
            call.respond(carDAO.update(id, car))
        }

        delete("/cars/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(carDAO.delete(id))
        }
    }
}