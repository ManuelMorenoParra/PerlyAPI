package routes

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
            call.respond(retos)
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            val reto = RetosService.getRetoById(id)
            if (reto == null) {
                call.respond(HttpStatusCode.NotFound, "Reto no encontrado")
                return@get
            }

            call.respond(reto)
        }

        post {
            val request = call.receive<RetoDTO>()
            val nuevoReto = RetosService.createReto(request)
            call.respond(HttpStatusCode.Created, nuevoReto)
        }

        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }

            val request = call.receive<RetoDTO>()
            val updated = RetosService.updateReto(id, request)
            if (!updated) {
                call.respond(HttpStatusCode.NotFound, "Reto no encontrado")
                return@put
            }

            call.respond(HttpStatusCode.OK, "Reto actualizado")
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@delete
            }

            val deleted = RetosService.deleteReto(id)
            if (!deleted) {
                call.respond(HttpStatusCode.NotFound, "Reto no encontrado")
                return@delete
            }

            call.respond(HttpStatusCode.OK, "Reto eliminado")
        }
    }
}
