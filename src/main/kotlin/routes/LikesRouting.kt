package routes

import edu.gva.es.domain.LikeDTO
import edu.gva.es.services.LikesService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likesRouting() {

    route("/likes") {

        post {
            try {
                val dto = call.receive<LikeDTO>()
                // Llamada al servicio correspondiente
                // LikesService.darLike(dto)
                call.respond(HttpStatusCode.Created, "Like registrado correctamente")
            } catch (e: Exception) {
                val cause = e.cause?.message ?: e.message ?: ""
                when {
                    cause.contains("Duplicate entry") ->
                        call.respond(HttpStatusCode.Conflict, "Ya has dado like a esta publicación")
                    cause.contains("foreign key constraint fails") ->
                        call.respond(HttpStatusCode.BadRequest, "Error: El usuario o la publicación no existen")
                    else ->
                        call.respond(HttpStatusCode.InternalServerError, "Error: $cause")
                }
            }
        }

        delete("/{usuario}/{pub}") {
            val u = call.parameters["usuario"]?.toIntOrNull()
            val p = call.parameters["pub"]?.toIntOrNull()

            if (u != null && p != null) {
                LikesService.quitarLike(u, p)
                call.respond(HttpStatusCode.OK, "Like eliminado")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Parámetros de URL inválidos")
            }
        }

        get("/publicacion/{id}") {
            val idPub = call.parameters["id"]?.toIntOrNull()
            if (idPub != null) {
                // Llamada al servicio: val total = LikesService.contarPorPublicacion(idPub)
                call.respond(HttpStatusCode.OK, mapOf("total" to 1)) // Ejemplo estático
            } else {
                call.respond(HttpStatusCode.BadRequest, "ID de publicación inválido")
            }
        }
    }
}
