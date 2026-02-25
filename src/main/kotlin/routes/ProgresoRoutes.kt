package edu.gva.es.routes

import edu.gva.es.domain.ProgresosDTO
import edu.gva.es.services.ProgresosService // Nombre corregido
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.progresoRouting() {
    val service = ProgresosService()

    route("/progresos") {
        get("/usuario/{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(service.obtenerProgresoUsuario(id))
        }

        post {
            val dto = call.receive<ProgresosDTO>()
            val id = service.registrarProgreso(dto)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        get("/puntos/{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(HttpStatusCode.OK, mapOf("puntosTotales" to service.obtenerPuntosTotales(id)))
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (service.eliminarProgreso(id)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val dto = call.receive<ProgresosDTO>()
            if (service.editarProgreso(id, dto)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }
    }
}