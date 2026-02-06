package routes

import domain.ProgresoDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.ProgresoService

fun Route.progresoRouting() {

    val service = ProgresoService()

    route("/progresos") {

        get("{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(service.obtenerProgresoUsuario(id))
        }

        post {
            try {
                val progreso = call.receive<ProgresoDTO>()
                val id = service.registrarProgreso(progreso)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            } catch (e: Exception) {
                // Esto te imprimirá el error real en la consola de IntelliJ
                println("Error en POST /progreso: ${e.message}")
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido")
            }
        }

        get("puntos/{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val puntos = service.obtenerPuntosTotales(id)
            call.respond(mapOf("puntosTotales" to puntos))
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID de progreso no válido")
                return@put
            }

            try {
                val dto = call.receive<ProgresoDTO>()
                val exito = service.editarProgreso(id, dto)

                if (exito) {
                    call.respond(HttpStatusCode.OK, "Progreso actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el progreso con ID $id")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error al actualizar: ${e.message}")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID de progreso inválido")
                return@delete
            }

            try {
                // Asegúrate de que tu ProgresoService tenga una función delete(id: Int)
                val eliminado = service.eliminarProgreso(id)

                if (eliminado) {
                    call.respond(HttpStatusCode.OK, "Progreso eliminado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró el progreso con ID $id")
                }
            } catch (e: Exception) {
                println("Error al eliminar progreso: ${e.message}")
                call.respond(HttpStatusCode.InternalServerError, "Error en el servidor: ${e.message}")
            }
        }
    }
}
