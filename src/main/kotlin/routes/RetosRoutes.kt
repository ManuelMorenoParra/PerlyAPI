package routes

import domain.RetoDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.RetosService

fun Route.retosRouting() {

    val service = RetosService()

    route("/retos") {

        get {
            call.respond(service.getAllRetos())
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            val reto = service.getRetoById(id)
            if (reto != null)
                call.respond(reto)
            else
                call.respond(HttpStatusCode.NotFound, "Reto no encontrado")
        }

        post {
            val reto = call.receive<RetoDTO>()
            val id = service.createReto(reto)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }

            val reto = call.receive<RetoDTO>()
            val actualizado = service.updateReto(id, reto)

            if (actualizado)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@delete
            }

            val eliminado = service.deleteReto(id)

            if (eliminado)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}
