package edu.gva.es.routes

import edu.gva.es.domain.SoportesDTO
import edu.gva.es.services.SoportesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.soportesRouting() {
    val service = SoportesService

    route("/soportes") {

        // GET /soportes/usuario/1 -> Listar tickets del usuario
        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID de usuario no válido")

            val tickets = service.obtenerMisTickets(id)
            call.respond(tickets)
        }

        // POST /soportes -> Crear un nuevo ticket
        post {
            try {

                val ticketReq = call.receive<SoportesDTO>()
                val ticketFinal = ticketReq.copy(estado = "open")

                val idGenerado = service.crearTicket(ticketFinal)
                call.respond(HttpStatusCode.Created, mapOf("id" to idGenerado))
            } catch (e: Exception) {

                application.log.error("Error creando ticket: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, "Error en el formato del ticket")
            }
        }

        // PUT /soportes/responder/{id} -> Responder y cerrar ticket (Admin)
        put("/responder/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID de ticket no válido")

            try {
                val cuerpo = call.receive<Map<String, String>>()
                val respuesta = cuerpo["respuesta"]

                if (respuesta.isNullOrBlank()) {
                    return@put call.respond(HttpStatusCode.BadRequest, "La respuesta no puede estar vacía")
                }

                service.responderTicket(id, respuesta)
                call.respond(HttpStatusCode.OK, mapOf("status" to "Ticket respondido y marcado como resolved"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error al procesar la respuesta")
            }
        }
    }
}