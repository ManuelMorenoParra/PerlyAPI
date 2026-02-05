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
            try {
                val mensaje = call.receive<MensajeDTO>()
                val idGenerado = service.enviarMensaje(mensaje)
                call.respond(HttpStatusCode.Created, idGenerado)
            } catch (e: IllegalStateException) {
                // Si saltó el bloqueo, respondemos 403 (Prohibido)
                call.respond(HttpStatusCode.Forbidden, e.message ?: "Acceso denegado")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error al enviar mensaje")
            }
        }

        put("/{id}") {
            // 1. Intentamos obtener el ID
            val idParam = call.parameters["id"]?.toIntOrNull()

            // 2. Si es nulo, respondemos BadRequest y salimos de la función con 'return@put'
            if (idParam == null) {
                call.respond(HttpStatusCode.BadRequest, "El ID debe ser un número entero válido")
                return@put
            }

            // 3. Ahora 'idParam' ya es de tipo 'Int' (no nulo).
            // IntelliJ ya no marcará error al pasarlo al service.
            try {
                val dto = call.receive<MensajeDTO>()
                val exito = service.actualizarMensaje(idParam, dto)

                if (exito) {
                    call.respond(HttpStatusCode.OK, "Mensaje actualizado")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el mensaje")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato del JSON: ${e.message}")
            }
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
