package edu.gva.es.routes

import edu.gva.es.domain.BloqueoDTO
import edu.gva.es.services.BloqueosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bloqueosRouting() {
    val service = BloqueosService()

    route("/bloqueos") {
        get {
            val bloqueos = service.obtenerTodos()
            if (bloqueos.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(bloqueos)
            }
        }

        post {
            try {
                val dto = call.receive<BloqueoDTO>()
                if (dto.idBloqueador == dto.idBloqueado) {
                    return@post call.respond(HttpStatusCode.BadRequest, "No puedes bloquearte a ti mismo")
                }
                val id = service.bloquearUsuario(dto)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error: ${e.message}")
            }
        }

        delete("/{bloqueador}/{bloqueado}") {
            val bloqueador = call.parameters["bloqueador"]?.toIntOrNull()
            val bloqueado = call.parameters["bloqueado"]?.toIntOrNull()
            if (bloqueador == null || bloqueado == null) return@delete call.respond(HttpStatusCode.BadRequest, "IDs inválidos")

            if (service.desbloquearUsuario(bloqueador, bloqueado)) {
                call.respond(HttpStatusCode.OK, "Desbloqueado")
            } else {
                call.respond(HttpStatusCode.NotFound, "No encontrado")
            }
        }
    }
}