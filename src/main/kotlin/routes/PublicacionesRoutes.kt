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

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val eliminado = service.delete(id)

            if (eliminado)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}
