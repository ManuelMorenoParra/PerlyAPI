package routes

import domain.BloqueoDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.BloqueosService
import services.ComentariosService

fun Route.bloqueosRouting() {
    val service = BloqueosService()

    route("/bloqueos") {
        // POST: Bloquear
        post {
            val dto = call.receive<BloqueoDTO>()
            if (service.bloquearUsuario(dto)) {
                call.respond(HttpStatusCode.Created, "Usuario bloqueado")
            } else {
                call.respond(HttpStatusCode.Conflict, "Ya estaba bloqueado o error")
            }
        }

        // DELETE: Desbloquear
        delete("/{bloqueador}/{bloqueado}") {
            val bloqueador = call.parameters["bloqueador"]?.toIntOrNull()
            val bloqueado = call.parameters["bloqueado"]?.toIntOrNull()

            if (bloqueador != null && bloqueado != null) {
                if (service.desbloquearUsuario(bloqueador, bloqueado)) {
                    call.respond(HttpStatusCode.OK, "Usuario desbloqueado")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No existía ese bloqueo")
                }
            }
        }
    }
}