package edu.gva.es.routes

import edu.gva.es.domain.*
import edu.gva.es.services.UsuariosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.usuarioRouting() {
    val service = UsuariosService

    route("/usuarios") {
        get { call.respond(service.listarUsuarios()) }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = service.buscarPorId(id)
            if (user != null) call.respond(user) else call.respond(HttpStatusCode.NotFound)
        }

        // Registrar usuario (Sign Up)
        post {
            val user = call.receive<UsuariosDTO>()
            val id = service.registrarUsuario(user)
            if (id != -1) call.respond(HttpStatusCode.Created, mapOf("id" to id))
            else call.respond(HttpStatusCode.Conflict, "El email ya existe")
        }

        // NUEVO: Actualizar perfil (Bio, Privacidad, Achievements)
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val datos = call.receive<UsuariosDTO>()
            if (service.actualizarPerfil(id, datos)) {
                call.respond(HttpStatusCode.OK, "Perfil actualizado")
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            service.eliminarUsuarioCompleto(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    route("/auth") {
        post("/login") {
            val login = call.receive<LoginRequest>()
            val usuario = service.login(login.email, login.password)
            if (usuario != null) {
                // Devolvemos el usuario completo para que Angular tenga el ID, nombre y avatar
                call.respond(HttpStatusCode.OK, usuario)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Email o contraseña incorrectos")
            }
        }
    }
}