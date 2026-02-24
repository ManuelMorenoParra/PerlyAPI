package edu.gva.es.routes

import edu.gva.es.services.UsuariosService
import edu.gva.es.domain.UsuarioDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import edu.gva.es.domain.UserSession
import edu.gva.es.domain.LoginRequest
import edu.gva.es.domain.*

fun Route.usuarioRouting() {

    val service = UsuariosService

    route("/auth") {

        post("/login") {
            try {
                val login = call.receive<LoginRequest>()

                if (login.email.isBlank() || login.password.isBlank()) {
                    return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Email y contraseña requeridos"))
                }

                val usuario = service.buscarPorEmail(login.email)

                if (usuario != null && service.login(login.email, login.password)) {

                    call.sessions.set(UserSession(email = login.email))
                    call.respond(HttpStatusCode.OK, mapOf(
                        "message" to "Login exitoso",
                        "userId" to usuario.id,
                        "userEmail" to usuario.email
                    ))
                } else {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Credenciales incorrectas"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de login inválido"))
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respond(HttpStatusCode.OK, mapOf("message" to "Sesión cerrada correctamente"))
        }
    }

    route("/usuarios") {

        get {
            val lista = service.listarUsuarios()
            if (lista.isEmpty()) call.respond(HttpStatusCode.NoContent)
            else call.respond(HttpStatusCode.OK, lista)
        }

        post {
            try {
                val usuario = call.receive<UsuarioDTO>()

                if (!usuario.email.contains("@")) {
                    return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de email inválido"))
                }

                val nuevoId = service.registrarUsuario(usuario)

                if (nuevoId != -1) {
                    call.respond(HttpStatusCode.Created, mapOf("id" to nuevoId, "message" to "Usuario registrado con éxito"))
                } else {

                    call.respond(HttpStatusCode.Conflict, mapOf("error" to "El email ya se encuentra registrado"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Error en los datos: ${e.message}"))
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID no válido"))

            val usuario = service.buscarPorId(id)
            if (usuario != null) call.respond(HttpStatusCode.OK, usuario)
            else call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID no válido"))

            try {
                val usuarioActualizado = call.receive<UsuarioDTO>()
                val filas = service.actualizarUsuario(id, usuarioActualizado)

                if (filas > 0) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Usuario actualizado correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Error en los datos de actualización"))
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID no válido"))

            try {
                service.eliminar(id)
                call.respond(HttpStatusCode.OK, mapOf("message" to "Usuario eliminado correctamente"))
            } catch (e: Exception) {
                val cause = e.cause?.message ?: e.message ?: ""
                if (cause.contains("foreign key constraint fails")) {

                    call.respond(HttpStatusCode.Conflict, mapOf(
                        "error" to "No se puede borrar el usuario",
                        "reason" to "El usuario tiene datos asociados (publicaciones, likes, etc.)",
                        "solution" to "Usa el endpoint /usuarios/full/$id para una eliminación en cascada"
                    ))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to cause))
                }
            }
        }

        delete("/full/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID no válido"))

            try {
                service.eliminarUsuarioCompleto(id)
                call.respond(HttpStatusCode.OK, mapOf("message" to "Usuario $id y todos sus datos eliminados con éxito"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error en la limpieza total: ${e.message}"))
            }
        }
    }
}