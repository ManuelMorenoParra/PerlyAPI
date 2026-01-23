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

    route("/progreso") {

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
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
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
    }
}
