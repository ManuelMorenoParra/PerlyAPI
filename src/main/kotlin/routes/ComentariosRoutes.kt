package routes

import domain.ComentarioDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.ComentariosService

fun Route.comentariosRouting() {

    val service = ComentariosService()

    route("/comentarios") {

        get("{idPublicacion}") {
            val id = call.parameters["idPublicacion"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(service.getComentariosDePublicacion(id))
        }

        post {
            val comentario = call.receive<ComentarioDTO>()
            val id = service.crearComentario(comentario)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val eliminado = service.eliminarComentario(id)

            if (eliminado)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}
