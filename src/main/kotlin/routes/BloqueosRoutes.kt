package edu.gva.es.routes

import domain.BloqueoDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.BloqueosService

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

                if (id > 0) {

                    call.respond(HttpStatusCode.Created, mapOf("id" to id))
                } else {

                    call.respond(HttpStatusCode.Conflict, "Este bloqueo ya existe")
                }
            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, "Formato de datos inválido")
            }
        }

        delete("/{bloqueador}/{bloqueado}") {
            val bloqueador = call.parameters["bloqueador"]?.toIntOrNull()
            val bloqueado = call.parameters["bloqueado"]?.toIntOrNull()

            if (bloqueador == null || bloqueado == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "Los IDs deben ser numéricos")
            }

            if (service.desbloquearUsuario(bloqueador, bloqueado)) {

                call.respond(HttpStatusCode.OK, "Usuario desbloqueado correctamente")
            } else {

                call.respond(HttpStatusCode.NotFound, "No se encontró el registro de bloqueo")
            }
        }
    }
}