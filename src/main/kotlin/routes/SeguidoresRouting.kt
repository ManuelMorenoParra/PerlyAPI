package edu.gva.es.routes

import edu.gva.es.domain.SeguidorDTO
import edu.gva.es.services.SeguidoresService
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
                SeguidoresService.seguir(dto)
                call.respond(HttpStatusCode.Created, "Seguimiento creado correctamente")
            } catch (e: Exception) {
                val errorFull = e.cause?.message ?: e.message ?: ""
                when {
                    errorFull.contains("Duplicate entry") ->
                        call.respond(HttpStatusCode.Conflict, "Ya sigues a este usuario")
                    else ->
                        call.respond(HttpStatusCode.InternalServerError, "Error: $errorFull")
                }
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID no válido")

            // CAMBIADO: Antes decía obtenerSeguidores, pero en tu Service se llama 'listar'
            val lista = SeguidoresService.listar(id)
            call.respond(lista)
        }

        delete("/{idUsuario}/{idSeguido}") {
            val idUser = call.parameters["idUsuario"]?.toIntOrNull()
            val idFollowed = call.parameters["idSeguido"]?.toIntOrNull()

            if (idUser == null || idFollowed == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "IDs no válidos")
            }

            val eliminados = SeguidoresService.dejar(idUser, idFollowed)

            if (eliminados > 0) {
                call.respond(HttpStatusCode.OK, "Ya no sigues a este usuario")
            } else {
                call.respond(HttpStatusCode.NotFound, "No se encontró la relación")
            }
        }
    }
}