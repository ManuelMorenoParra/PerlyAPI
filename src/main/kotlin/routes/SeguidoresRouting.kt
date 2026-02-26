package edu.gva.es.routes

import edu.gva.es.services.SeguidoresService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.seguidoresRouting() {
    val service = SeguidoresService

    route("/seguidores") {

        // POST /seguidores/follow/1/2
        post("/follow/{idSeguidor}/{idSeguido}") {
            val seguidor = call.parameters["idSeguidor"]?.toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val seguido = call.parameters["idSeguido"]?.toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest)

            service.seguirUsuario(seguidor, seguido)
            call.respond(HttpStatusCode.Created, "Ahora sigues a este usuario")
        }

        // DELETE /seguidores/unfollow/1/2
        delete("/unfollow/{idSeguidor}/{idSeguido}") {
            val seguidor = call.parameters["idSeguidor"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val seguido = call.parameters["idSeguido"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)

            service.dejarDeSeguir(seguidor, seguido)
            call.respond(HttpStatusCode.OK, "Has dejado de seguir a este usuario")
        }

        // GET /seguidores/stats/1
        get("/stats/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(service.obtenerEstadisticas(id))
        }
    }
}