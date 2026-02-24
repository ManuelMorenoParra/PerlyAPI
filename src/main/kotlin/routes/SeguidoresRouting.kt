package edu.gva.es.routes

import edu.gva.es.domain.SeguidorDTO
import edu.gva.es.services.SeguidoresService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import edu.gva.es.domain.*

fun Route.seguidoresRouting() {
    route("/seguidores") {

        post {
            try {
                val dto = call.receive<SeguidorDTO>()

                if (dto.idSeguidor == dto.idSeguido) {
                    return@post call.respond(HttpStatusCode.BadRequest, "Un usuario no puede seguirse a sí mismo")
                }

                SeguidoresService.seguir(dto)
                call.respond(HttpStatusCode.Created, mapOf("message" to "Seguimiento creado correctamente"))
            } catch (e: Exception) {
                val message = e.message ?: ""
                when {

                    message.contains("Duplicate entry") ->
                        call.respond(HttpStatusCode.Conflict, "Ya sigues a este usuario")
                    message.contains("foreign key constraint fails") ->
                        call.respond(HttpStatusCode.NotFound, "Uno de los usuarios no existe")
                    else ->
                        call.respond(HttpStatusCode.BadRequest, "Error en el formato de la solicitud")
                }
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "El ID debe ser un número válido")

            val lista = SeguidoresService.listar(id)
            if (lista.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.OK, lista)
            }
        }

        delete("/{idUsuario}/{idSeguido}") {
            val idUser = call.parameters["idUsuario"]?.toIntOrNull()
            val idFollowed = call.parameters["idSeguido"]?.toIntOrNull()

            if (idUser == null || idFollowed == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "IDs de usuario inválidos")
            }

            val eliminados = SeguidoresService.dejar(idUser, idFollowed)
            if (eliminados > 0) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Has dejado de seguir al usuario"))
            } else {
                call.respond(HttpStatusCode.NotFound, "No existe la relación de seguimiento")
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID no válido")

            try {
                val dto = call.receive<SeguidorDTO>()
                val actualizado = SeguidoresService.editarSeguimiento(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Seguimiento actualizado"))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el registro con ID $id")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en los datos de actualización")
            }
        }
    }
}