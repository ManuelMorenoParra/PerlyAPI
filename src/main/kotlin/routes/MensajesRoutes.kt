package edu.gva.es.routes

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

        get("/usuario/{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull()
            if (id == null) {
                return@get call.respond(HttpStatusCode.BadRequest, "ID de usuario inválido")
            }

            val mensajes = service.getMensajesUsuario(id)
            if (mensajes.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.OK, mensajes)
            }
        }

        post {
            try {
                val mensaje = call.receive<MensajeDTO>()

                if (mensaje.contenido.isBlank()) {
                    return@post call.respond(HttpStatusCode.BadRequest, "El mensaje no puede estar vacío")
                }

                val idGenerado = service.enviarMensaje(mensaje)
                call.respond(HttpStatusCode.Created, mapOf("id" to idGenerado))
            } catch (e: IllegalStateException) {

                call.respond(HttpStatusCode.Forbidden, mapOf("error" to (e.message ?: "Acceso denegado")))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de la solicitud")
            }
        }

        put("/{id}") {
            val idParam = call.parameters["id"]?.toIntOrNull()
            if (idParam == null) {
                return@put call.respond(HttpStatusCode.BadRequest, "El ID debe ser un número entero válido")
            }

            try {
                val dto = call.receive<MensajeDTO>()
                val exito = service.actualizarMensaje(idParam, dto)

                if (exito) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Mensaje actualizado"))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el mensaje con ID $idParam")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Cuerpo JSON inválido")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "ID requerido")
            }

            if (service.eliminarMensaje(id)) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Mensaje eliminado"))
            } else {
                call.respond(HttpStatusCode.NotFound, "El mensaje no existe")
            }
        }
    }
}