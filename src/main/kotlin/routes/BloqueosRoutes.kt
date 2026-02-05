package routes

import domain.BloqueoDTO
import edu.gva.es.data.BloqueosDAO
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
        // GET: Ahora funcionará y devolverá la lista
        get {
            call.respond(service.obtenerTodos())
        }

        // POST: Bloquear
        post {
            try {
                // Especificamos <BloqueoDTO> para que Ktor sepa a qué clase mapear el JSON
                val dto = call.receive<BloqueoDTO>()
                val id = service.bloquearUsuario(dto)
                call.respond(HttpStatusCode.Created, id)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato del JSON: ${e.message}")
            }
        }

        // DELETE: Desbloquear usuario B por parte de A
        // URL: /bloqueos/1/2
        delete("/{bloqueador}/{bloqueado}") {
            val bloqueador = call.parameters["bloqueador"]?.toIntOrNull()
            val bloqueado = call.parameters["bloqueado"]?.toIntOrNull()

            if (bloqueador != null && bloqueado != null) {
                if (service.desbloquearUsuario(bloqueador, bloqueado)) {
                    call.respond(HttpStatusCode.OK, "Ya no están bloqueados")
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se encontró ese bloqueo")
                }
            }
        }
    }
}