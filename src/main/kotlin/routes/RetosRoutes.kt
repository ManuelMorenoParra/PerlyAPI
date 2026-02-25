package edu.gva.es.routes

import edu.gva.es.domain.RetosDTO
import edu.gva.es.services.RetosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.retosRouting() {
    route("/retos") {
        get { call.respond(RetosService.getAllRetos()) }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val reto = RetosService.getRetoById(id)
            if (reto != null) call.respond(reto) else call.respond(HttpStatusCode.NotFound)
        }

        post {
            val request = call.receive<RetosDTO>()
            val nuevo = RetosService.createReto(request)
            call.respond(HttpStatusCode.Created, nuevo)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val request = call.receive<RetosDTO>()
            if (RetosService.updateReto(id, request)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (RetosService.deleteReto(id)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }
    }
}