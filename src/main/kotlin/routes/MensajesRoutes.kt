package routes

import domain.MensajeDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.MensajesService

fun Route.mensajesRouting() {

    val service = MensajesService()

    route("/mensajes") {

        get("{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(service.getMensajesUsuario(id))
        }

        post {
            val mensaje = call.receive<MensajeDTO>()
            val id = service.enviarMensaje(mensaje)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        put("leido/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val actualizado = service.marcarLeido(id)

            if (actualizado)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val eliminado = service.eliminarMensaje(id)

            if (eliminado)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}
