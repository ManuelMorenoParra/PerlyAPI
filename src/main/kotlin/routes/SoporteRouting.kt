package routes

import edu.gva.es.domain.SoporteDTO
import edu.gva.es.services.SoporteService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.soporteRouting() {

    route("/soporte") {

        post {
            val dto = call.receive<SoporteDTO>()
            SoporteService.crear(dto)
            call.respondText("Ticket creado")
        }

        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val respuesta = call.receiveText()

            SoporteService.responder(id, respuesta)
            call.respondText("Ticket respondido")
        }

        get("/{u}") {
            call.respond(
                SoporteService.listar(
                    call.parameters["u"]!!.toInt()
                )
            )
        }
    }
}
