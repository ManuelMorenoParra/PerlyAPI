package routes

import domain.PublicacionDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.PublicacionesService

fun Route.publicacionesRouting() {

    val service = PublicacionesService()

    route("/publicaciones") {

        // 🔹 Obtener todas las publicaciones
        get {
            call.respond(service.getAll())
        }

        // 🔹 Obtener publicaciones de un usuario
        get("usuario/{idUsuario}") {
            val idUsuario = call.parameters["idUsuario"]?.toIntOrNull()
            if (idUsuario == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(service.getByUsuario(idUsuario))
        }

        // 🔹 Crear publicación
        post {
            val pub = call.receive<PublicacionDTO>()
            val id = service.create(pub)
            call.respond(HttpStatusCode.Created, mapOf("id" to id))
        }

        // 🔹 Eliminar publicación
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val eliminado = service.delete(id)

            if (eliminado)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}
