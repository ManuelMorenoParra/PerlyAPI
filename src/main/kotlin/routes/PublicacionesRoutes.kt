package routes

import domain.PublicacionDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.PublicacionesService

fun Route.publicacionesRouting() {
    val service = PublicacionesService()

    route("/publicaciones") {

        get {
            call.respond(service.getAll())
        }

        post {
            val pub = call.receive<PublicacionDTO>()
            val id = service.create(pub)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID de publicación no válido")
                return@put
            }

            try {

                val dto = call.receive<PublicacionDTO>()

                val actualizado = service.editarPublicacion(id, dto)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, "Publicación actualizada")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró la publicación con ID $id")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (service.delete(id)) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
        }
    }
}