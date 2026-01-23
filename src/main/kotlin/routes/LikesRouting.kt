package routes

import edu.gva.es.domain.LikeDTO
import edu.gva.es.services.LikesService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likesRouting() {

    route("/likes") {

        post {
            val dto = call.receive<LikeDTO>()
            LikesService.darLike(dto)
            call.respondText("Like añadido")
        }

        delete("/{usuario}/{pub}") {
            val u = call.parameters["usuario"]!!.toInt()
            val p = call.parameters["pub"]!!.toInt()

            LikesService.quitarLike(u, p)
            call.respondText("Like eliminado")
        }

        get("/{pub}") {
            val p = call.parameters["pub"]!!.toInt()
            call.respond(LikesService.contar(p))
        }
    }
}
