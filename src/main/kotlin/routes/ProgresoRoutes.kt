package edu.gva.es.routes

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

        get("/usuario/{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull()

            if (id == null) {
                return@get call.respond(HttpStatusCode.BadRequest, "El ID de usuario debe ser un número")
            }

            val progreso = service.obtenerProgresoUsuario(id)
            if (progreso.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.OK, progreso)
            }
        }

        post {
            try {
                val progreso = call.receive<ProgresoDTO>()

                if (progreso.puntos < 0) {
                    return@post call.respond(HttpStatusCode.BadRequest, "Los puntos no pueden ser negativos")
                }

                val idGenerado = service.registrarProgreso(progreso)
                call.respond(HttpStatusCode.Created, mapOf("id" to idGenerado))
            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, "Formato de progreso inválido")
            }
        }

        get("/puntos/{idUsuario}") {
            val id = call.parameters["idUsuario"]?.toIntOrNull()

            if (id == null) {
                return@get call.respond(HttpStatusCode.BadRequest, "ID inválido")
            }

            val puntos = service.obtenerPuntosTotales(id)

            call.respond(HttpStatusCode.OK, mapOf(
                "idUsuario" to id,
                "puntosTotales" to puntos
            ))
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@put call.respond(HttpStatusCode.BadRequest, "ID de progreso no válido")
            }

            try {
                val dto = call.receive<ProgresoDTO>()
                val exito = service.editarProgreso(id, dto)

                if (exito) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Progreso actualizado"))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No existe el registro con ID $id")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en los datos de actualización")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "ID de progreso inválido")
            }

            if (service.eliminarProgreso(id)) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Registro eliminado correctamente"))
            } else {
                call.respond(HttpStatusCode.NotFound, "No se encontró el registro para eliminar")
            }
        }
    }
}