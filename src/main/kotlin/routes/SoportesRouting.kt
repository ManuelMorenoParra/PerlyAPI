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

        // GET /soportes/usuario/1
        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(service.obtenerMisTickets(id))
        }

        // POST /soportes
        post {
            try {
                val ticket = call.receive<SoportesDTO>()
                val idGenerado = service.crearTicket(ticket)
                call.respond(HttpStatusCode.Created, mapOf("id" to idGenerado))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en los datos del ticket")
            }
        }

        // PUT /soportes/responder/5 (Para uso administrativo)
        put("/responder/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val cuerpo = call.receive<Map<String, String>>()
            val respuesta = cuerpo["respuesta"] ?: ""

            service.responderTicket(id, respuesta)
            call.respond(HttpStatusCode.OK, "Ticket respondido")
        }
    }
}