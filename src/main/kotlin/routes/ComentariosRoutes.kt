package edu.gva.es.routes

import edu.gva.es.domain.ComentarioDTO
import edu.gva.es.services.ComentariosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.comentariosRouting() {

    val service = ComentariosService()

    route("/comentarios") {

        // OBTENER COMENTARIOS DE UNA PUBLICACIÓN
        get("/publicacion/{idPublicacion}") {
            val id = call.parameters["idPublicacion"]?.toIntOrNull()
            if (id == null) {
                return@get call.respond(HttpStatusCode.BadRequest, "El ID de la publicación debe ser numérico")
            }

            val lista = service.getComentariosDePublicacion(id)

            if (lista.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.OK, lista)
            }
        }

        // CREAR COMENTARIO
        post {
            try {
                val comentario = call.receive<ComentarioDTO>()

                if (comentario.contenido.isBlank()) {
                    return@post call.respond(HttpStatusCode.BadRequest, "El contenido del comentario no puede estar vacío")
                }

                val id = service.crearComentario(comentario)
                if (id > 0) {
                    call.respond(HttpStatusCode.Created, mapOf("id" to id))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "No se pudo crear el comentario")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato del JSON: ${e.message}")
            }
        }

        // ACTUALIZAR COMENTARIO
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@put call.respond(HttpStatusCode.BadRequest, "ID de comentario no válido")
            }

            try {
                val dto = call.receive<ComentarioDTO>()
                val actualizado = service.actualizarComentario(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Comentario actualizado con éxito"))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el comentario con ID $id")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de actualización inválidos")
            }
        }

        // ELIMINAR COMENTARIO
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "ID requerido")
            }

            if (service.eliminarComentario(id)) {
                call.respond(HttpStatusCode.OK, "Comentario eliminado")
            } else {
                call.respond(HttpStatusCode.NotFound, "El comentario no existe")
            }
        }
    }
}