package edu.gva.es.routes

import edu.gva.es.domain.BloqueosDTO
import edu.gva.es.services.BloqueosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bloqueosRouting() {
    val service = BloqueosService

    route("/bloqueos") {

        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID de usuario inválido")
            call.respond(service.listarBloqueadosPorUsuario(id))
        }

        post {
            try {
                val dto = call.receive<BloqueosDTO>()
                val idGenerado = service.bloquearUsuario(dto)
                call.respond(HttpStatusCode.Created, mapOf("id" to idGenerado))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de datos")
            }
        }

        delete("/eliminar") {
            val bloqueador = call.request.queryParameters["bloqueador"]?.toIntOrNull()
            val bloqueado = call.request.queryParameters["bloqueado"]?.toIntOrNull()
            val tipo = call.request.queryParameters["tipo"] ?: "block"

            if (bloqueador == null || bloqueado == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "Faltan parámetros")
            }

            if (service.eliminarBloqueo(bloqueador, bloqueado, tipo)) {
                call.respond(HttpStatusCode.OK, "Restricción eliminada")
            } else {
                call.respond(HttpStatusCode.NotFound, "No se encontró el bloqueo")
            }
        }
    }
}