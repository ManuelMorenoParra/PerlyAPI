package edu.gva.es.routes

import edu.gva.es.domain.SoporteDTO
import edu.gva.es.services.SoporteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import edu.gva.es.domain.*

fun Route.soporteRouting() {

    route("/soportes") {

        post {
            try {
                val dto = call.receive<SoporteDTO>()

                if (dto.mensaje.isBlank()) {
                    return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El mensaje del ticket no puede estar vacío"))
                }

                val idGenerado = SoporteService.crear(dto)
                call.respond(HttpStatusCode.Created, mapOf("id" to idGenerado, "message" to "Ticket creado correctamente"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Error en el formato de los datos"))
            }
        }

        get("/usuario/{u}") {
            val idUsuario = call.parameters["u"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de usuario inválido"))

            val tickets = SoporteService.listarPorUsuario(idUsuario)

            if (tickets.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.OK, tickets)
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID no válido"))

            try {
                val dto = call.receive<SoporteDTO>()
                val actualizado = SoporteService.editarSoporte(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Soporte actualizado correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "No se encontró el soporte con ID $id"))
                }
            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Error en el formato de datos enviado"))
            }
        }

        delete("/{id}") {
            val idSoporte = call.parameters["id"]?.toIntOrNull()

            if (idSoporte == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de soporte no válido"))
            }

            val eliminado = SoporteService.eliminar(idSoporte)

            if (eliminado) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Ticket $idSoporte eliminado correctamente"))
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "El ticket no existe"))
            }
        }
    }
}