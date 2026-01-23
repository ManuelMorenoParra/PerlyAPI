package routes

import edu.gva.es.domain.SeguidorDTO
import edu.gva.es.services.SeguidoresService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.seguidoresRouting() {

    route("/seguidores") {

        post {
            val dto = call.receive<SeguidorDTO>()
            SeguidoresService.seguir(dto)
            call.respondText("Seguimiento creado")
        }

        delete("/{u}/{s}") {
            SeguidoresService.dejar(
                call.parameters["u"]!!.toInt(),
                call.parameters["s"]!!.toInt()
            )
            call.respondText("Dejado de seguir")
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
