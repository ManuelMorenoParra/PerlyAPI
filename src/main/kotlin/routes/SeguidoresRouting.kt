package edu.gva.es.routes

import edu.gva.es.domain.SeguidorDTO
import edu.gva.es.services.SeguidoresService // Importa el object
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.seguidoresRouting() {
    route("/seguidores") {

        post {
            try {
                val dto = call.receive<SeguidorDTO>()
                // Llamada directa al object
                SeguidoresService.seguir(dto)
                call.respond(HttpStatusCode.Created, "Seguimiento creado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID no válido")

            val lista = SeguidoresService.listar(id) // Llamada directa
            call.respond(lista)
        }

        delete("/{idUsuario}/{idSeguido}") {
            val idUser = call.parameters["idUsuario"]?.toIntOrNull()
            val idFollowed = call.parameters["idSeguido"]?.toIntOrNull()

            if (idUser == null || idFollowed == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "IDs no válidos")
            }

            val eliminados = SeguidoresService.dejar(idUser, idFollowed) // Llamada directa
            if (eliminados > 0) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID no válido")

            try {

                val dto = call.receive<SeguidorDTO>()

                val actualizado = SeguidoresService.editarSeguimiento(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, "Seguimiento actualizado")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el registro")
                }
            } catch (e: Exception) {

                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
}