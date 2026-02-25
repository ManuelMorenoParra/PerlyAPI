package edu.gva.es.routes

import edu.gva.es.domain.PublicacionesDTO
import edu.gva.es.services.PublicacionesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.publicacionesRouting() {
    // CAMBIO CLAVE: No usamos (), llamamos directamente al object
    val service = PublicacionesService

    route("/publicaciones") {

        // Obtener todas las publicaciones
        get {
            call.respond(service.getAll())
        }

        // Obtener publicaciones de un usuario concreto
        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID inválido")
            call.respond(service.getByUsuario(id))
        }

        // Crear una nueva publicación
        post {
            try {
                val pub = call.receive<PublicacionesDTO>()
                val id = service.create(pub)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de datos")
            }
        }

        // Eliminar una publicación
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            if (service.delete(id)) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Editar una publicación
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest)

            val dto = call.receive<PublicacionesDTO>()
            if (service.editarPublicacion(id, dto)) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}