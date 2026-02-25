package edu.gva.es.routes

import edu.gva.es.domain.*
import edu.gva.es.services.UsuariosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.usuarioRouting() {
    val service = UsuariosService

    route("/usuarios") {
        get { call.respond(service.listarUsuarios()) }

        post {
            val user = call.receive<UsuarioDTO>()
            val id = service.registrarUsuario(user)
            if (id != -1) call.respond(HttpStatusCode.Created, id) else call.respond(HttpStatusCode.Conflict)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            service.eliminar(id)
            call.respond(HttpStatusCode.OK)
        }

        delete("/full/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            service.eliminarUsuarioCompleto(id)
            call.respond(HttpStatusCode.OK, "Eliminado todo")
        }
    }

    route("/auth") {
        post("/login") {
            val login = call.receive<LoginRequest>()
            if (service.login(login.email, login.password)) {
                call.respond(HttpStatusCode.OK, "Bienvenido")
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}