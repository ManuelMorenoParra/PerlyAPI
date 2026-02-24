package routes

import edu.gva.es.domain.LikeDTO
import edu.gva.es.services.LikesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likesRouting() {

    route("/likes") {

        post {
            try {
                val dto = call.receive<LikeDTO>()

                // Validación de entrada básica
                if (dto.idUsuario <= 0 || dto.idPublicacion <= 0) {
                    return@post call.respond(HttpStatusCode.BadRequest, "Los IDs de usuario y publicación deben ser válidos")
                }

                val registrado = LikesService.darLike(dto)

                if (registrado) {
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Like registrado correctamente"))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "No se pudo registrar el like")
                }
            } catch (e: Exception) {
                val cause = e.cause?.message ?: e.message ?: ""
                when {
                    cause.contains("Duplicate entry") ->
                        call.respond(HttpStatusCode.Conflict, "Ya has dado like a esta publicación")
                    cause.contains("foreign key constraint fails") ->
                        call.respond(HttpStatusCode.NotFound, "Error: El usuario o la publicación no existen")
                    else ->
                        call.respond(HttpStatusCode.BadRequest, "Formato de petición inválido")
                }
            }
        }

        delete("/{usuario}/{pub}") {
            val u = call.parameters["usuario"]?.toIntOrNull()
            val p = call.parameters["pub"]?.toIntOrNull()

            if (u == null || p == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "Parámetros de URL inválidos")
            }

            if (LikesService.quitarLike(u, p)) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Like eliminado"))
            } else {
                call.respond(HttpStatusCode.NotFound, "No se encontró el like para eliminar")
            }
        }

        get("/publicacion/{id}") {
            val idPub = call.parameters["id"]?.toIntOrNull()
            if (idPub == null) {
                return@get call.respond(HttpStatusCode.BadRequest, "ID de publicación inválido")
            }

            val total = LikesService.contarPorPublicacion(idPub)

            // Si la publicación no tiene likes, devolvemos 200 con total 0 o 204 No Content
            call.respond(HttpStatusCode.OK, mapOf("idPublicacion" to idPub, "total" to total))
        }

        // El método PUT en likes es inusual (un like se da o se quita),
        // pero lo dejamos con semántica robusta.
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")

            try {
                val dto = call.receive<LikeDTO>()
                val actualizado = LikesService.actualizarLike(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, "Like actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el like con ID $id")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Cuerpo de petición inválido")
            }
        }
    }
}