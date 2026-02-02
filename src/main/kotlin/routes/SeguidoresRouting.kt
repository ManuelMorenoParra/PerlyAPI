package routes

import edu.gva.es.domain.SeguidorDTO
import edu.gva.es.services.SeguidoresService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.seguidoresRouting() {

    route("/seguidores") {

        post {
            try {
                val dto = call.receive<SeguidorDTO>()
                SeguidoresService.seguir(dto)
                call.respond(HttpStatusCode.Created, "Seguimiento creado correctamente")
            } catch (e: Exception) {
                // Buscamos el mensaje de error en la excepción o en su causa original
                val errorFull = e.cause?.message ?: e.message ?: ""

                when {
                    errorFull.contains("Duplicate entry") ->
                        call.respond(HttpStatusCode.Conflict, "Ya sigues a este usuario")

                    errorFull.contains("foreign key constraint fails") ->
                        call.respond(HttpStatusCode.BadRequest, "Error: El usuario que intentas seguir no existe")

                    else ->
                        call.respond(HttpStatusCode.InternalServerError, "Error inesperado: $errorFull")
                }
            }
        }

        delete("/{u}/{s}") {
            val u = call.parameters["u"]?.toIntOrNull()
            val s = call.parameters["s"]?.toIntOrNull()

            if (u != null && s != null) {
                SeguidoresService.dejar(u, s)
                call.respond(HttpStatusCode.OK, "Dejado de seguir correctamente")
            } else {
                call.respond(HttpStatusCode.BadRequest, "IDs de parámetros inválidos")
            }
        }

        get("/{u}") {
            call.respond(
                SeguidoresService.listar(
                    call.parameters["u"]!!.toInt()
                )
            )
        }
    }
}
