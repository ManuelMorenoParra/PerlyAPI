package edu.gva.es.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import services.RetosService
import domain.RetoDTO

fun Route.retosRouting() {

    route("/retos") {

        get {
            val retos = RetosService.getAllRetos()
            if (retos.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.OK, retos)
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@get call.respond(HttpStatusCode.BadRequest, "El ID debe ser un número entero")
            }

            val reto = RetosService.getRetoById(id)
            if (reto == null) {
                call.respond(HttpStatusCode.NotFound, "No se ha encontrado el reto con ID $id")
            } else {
                call.respond(HttpStatusCode.OK, reto)
            }
        }

        post {
            try {
                val request = call.receive<RetoDTO>()

                if (request.nombre.isBlank()) {
                    return@post call.respond(HttpStatusCode.BadRequest, "El nombre del reto es obligatorio")
                }

                val nuevoRetoId = RetosService.createReto(request)
                call.respond(HttpStatusCode.Created, mapOf("id" to nuevoRetoId))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Formato de JSON incorrecto")
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")
            }

            try {
                val request = call.receive<RetoDTO>()
                val updated = RetosService.updateReto(id, request)

                if (updated) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Reto actualizado correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se pudo actualizar: el reto no existe")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de actualización inválidos")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido")
            }

            if (RetosService.deleteReto(id)) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Reto eliminado exitosamente"))
            } else {
                call.respond(HttpStatusCode.NotFound, "El reto no existe o ya fue eliminado")
            }
        }
    }
}