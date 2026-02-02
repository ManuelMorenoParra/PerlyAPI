package routes

import edu.gva.es.domain.SoporteDTO
import edu.gva.es.services.SoporteService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.soporteRouting() {

    route("/soportes") {

        post {
            try {
                val dto = call.receive<SoporteDTO>()
                SoporteService.crear(dto)
                call.respond(HttpStatusCode.Created, "Ticket creado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en los datos: ${e.message}")
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val respuesta = call.receiveText() // O call.receive<RespuestaDTO>() si prefieres JSON

            if (id != null) {
                SoporteService.responder(id, respuesta)
                call.respond(HttpStatusCode.OK, "Ticket $id respondido")
            } else {
                call.respond(HttpStatusCode.BadRequest, "ID no válido")
            }
        }

        get("/{u}") {
            call.respond(
                SoporteService.listar(
                    call.parameters["u"]!!.toInt()
                )
            )
        }

        // Eliminar un ticket por su ID
        delete("/{id}") {
            val idSoporte = call.parameters["id"]?.toIntOrNull()

            if (idSoporte != null) {
                try {
                    // Llamada al servicio: SoportesService.eliminar(idSoporte)
                    // Por ahora usamos una respuesta de éxito para probar en Postman
                    call.respond(HttpStatusCode.OK, "Ticket $idSoporte eliminado correctamente")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error al eliminar: ${e.message}")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "ID de soporte no válido o ausente")
            }
        }
    }
}
