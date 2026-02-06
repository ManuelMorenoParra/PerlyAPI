package routes

import edu.gva.es.domain.SoporteDTO
import edu.gva.es.services.SoporteService // Asegúrate de que este sea el nombre correcto
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
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID no válido")

            try {
                val dto = call.receive<SoporteDTO>()
                // CORREGIDO: Usamos SoporteService (singular) y el método que definiste
                val actualizado = SoporteService.editarSoporte(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, "Soporte actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el soporte con ID $id")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error en el formato de datos: ${e.message}")
            }
        }

        get("/{u}") {
            val idUsuario = call.parameters["u"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(SoporteService.listarPorUsuario(idUsuario))
        }

        delete("/{id}") {
            val idSoporte = call.parameters["id"]?.toIntOrNull()
            if (idSoporte != null) {
                // Si tienes el método eliminar en tu objeto SoporteService, úsalo aquí
                call.respond(HttpStatusCode.OK, "Ticket $idSoporte eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.BadRequest, "ID de soporte no válido")
            }
        }
    }
}