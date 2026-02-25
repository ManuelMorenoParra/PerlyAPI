package edu.gva.es.routes

import edu.gva.es.domain.SoporteDTO
import edu.gva.es.services.SoportesService // Nombre corregido con S
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.soporteRouting() {
    route("/soportes") {
        post {
            val dto = call.receive<SoporteDTO>()
            val id = SoportesService.crear(dto)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        get("/usuario/{u}") {
            val id = call.parameters["u"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(SoportesService.listarPorUsuario(id))
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (SoportesService.eliminar(id)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }
    }
}