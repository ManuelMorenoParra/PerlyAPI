package edu.gva.es.routes

import edu.gva.es.domain.PublicacionesDTO
import edu.gva.es.services.PublicacionesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.publicacionesRouting() {
    val service = PublicacionesService()

    route("/publicaciones") {
        get { call.respond(service.getAll()) }

        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(service.getByUsuario(id))
        }

        post {
            val pub = call.receive<PublicacionesDTO>()
            val id = service.create(pub)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (service.delete(id)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val dto = call.receive<PublicacionesDTO>()
            if (service.editarPublicacion(id, dto)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }
    }
}