package edu.gva.es.routes

import domain.PublicacionDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.PublicacionesService

fun Route.publicacionesRouting() {
    val service = PublicacionesService()

    route("/publicaciones") {

        get {
            val lista = service.getAll()
            if (lista.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.OK, lista)
            }
        }

        post {
            try {
                val pub = call.receive<PublicacionDTO>()

                if (pub.texto.isBlank() && pub.imagenBase64 == null) {
                    return@post call.respond(HttpStatusCode.BadRequest, "La publicación debe tener texto o imagen")
                }

                val id = service.create(pub)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de datos inválido"))
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@put call.respond(HttpStatusCode.BadRequest, "ID de publicación no válido")
            }

            try {
                val dto = call.receive<PublicacionDTO>()
                val actualizado = service.editarPublicacion(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Publicación actualizada correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró la publicación con ID $id")
                }
            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, "Error en los datos de actualización")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID requerido")

            if (service.delete(id)) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Publicación eliminada"))
            } else {
                call.respond(HttpStatusCode.NotFound, "La publicación no existe")
            }
        }
    }
}