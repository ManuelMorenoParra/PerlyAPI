package edu.gva.es.routes

import edu.gva.es.domain.ProgresosDTO
import edu.gva.es.services.ProgresosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.progresosRouting() {
    val service = ProgresosService()

    route("/progresos") {

        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID de usuario inválido")
            call.respond(service.obtenerProgresoUsuario(id))
        }

        get("/puntos/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID de usuario inválido")
            val puntos = service.obtenerPuntosTotales(id)
            call.respond(mapOf("puntosTotales" to puntos))
        }

        post {
            try {
                val dto = call.receive<ProgresosDTO>()
                val idGenerado = service.registrarProgreso(dto)
                call.respond(HttpStatusCode.Created, mapOf("id" to idGenerado))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de los datos")
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID de progreso inválido")
            val dto = call.receive<ProgresosDTO>()
            if (service.editarProgreso(id, dto)) {
                call.respond(HttpStatusCode.OK, "Progreso actualizado")
            } else {
                call.respond(HttpStatusCode.NotFound, "No se encontró el registro")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID de progreso inválido")
            if (service.eliminarProgreso(id)) {
                call.respond(HttpStatusCode.OK, "Progreso eliminado")
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}