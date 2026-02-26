package edu.gva.es.routes

import edu.gva.es.domain.PublicacionesDTO
import edu.gva.es.services.PublicacionesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.publicacionesRouting() {
    val service = PublicacionesService

    route("/publicaciones") {
        get {
            call.respond(service.getAll())
        }

        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID inválido")
            call.respond(service.getByUsuario(id))
        }

        post {
            try {
                val pub = call.receive<PublicacionesDTO>()
                val id = service.create(pub)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de datos: ${e.message}")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            if (service.delete(id)) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}