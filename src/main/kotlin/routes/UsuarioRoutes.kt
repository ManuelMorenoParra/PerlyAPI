package edu.gva.es.routes

import edu.gva.es.services.UsuariosService
import edu.gva.es.domain.UsuarioDTO
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import edu.gva.es.domain.UserSession
import edu.gva.es.domain.LoginRequest

fun Route.usuarioRouting() {

    val service = UsuariosService

    // Bloque de autenticación
    route("/auth") {
        post("/login") {
            val login = call.receive<LoginRequest>()
            val correcto = service.login(login.email, login.password)
            if (correcto) {
                call.sessions.set(UserSession(email = login.email))
                call.respond(HttpStatusCode.OK, "Login exitoso")
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Credenciales incorrectas")
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondText("Sesión cerrada correctamente.")
        }
    }

    route("/usuarios") {

        // 1. GET: Obtener todos
        get {
            val lista = service.listarUsuarios()
            call.respond(lista)
        }

        // 2. GET por ID
        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID no válido")
                return@get
            }

            val usuario = service.buscarPorId(id)
            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            }
        }

        // 3. POST: Crear nuevo usuario
        post {
            try {
                val usuario = call.receive<UsuarioDTO>()
                val nuevoId = service.registrarUsuario(usuario)

                if (nuevoId != -1)
                    call.respond(HttpStatusCode.Created, mapOf("id" to nuevoId))
                else
                    call.respond(HttpStatusCode.Conflict, "El email ya existe")

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en los datos: ${e.message}")
            }
        }

        // 4. PUT: Actualizar usuario
        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID no válido")
                return@put
            }

            val usuarioActualizado = call.receive<UsuarioDTO>()
            val filas = service.actualizarUsuario(id, usuarioActualizado)

            if (filas > 0) {
                call.respond(HttpStatusCode.OK, "Usuario actualizado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            }
        }

        // 5. DELETE: Eliminar usuario (Normal - Puede fallar si hay FK)
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                try {
                    service.eliminar(id)
                    call.respond(HttpStatusCode.OK, "Usuario eliminado correctamente")
                } catch (e: Exception) {
                    val cause = e.cause?.message ?: e.message ?: ""
                    if (cause.contains("foreign key constraint fails")) {
                        call.respond(HttpStatusCode.Conflict, "No se puede borrar: el usuario tiene datos asociados (mensajes, likes, etc.). Usa /usuarios/full/$id para limpieza total.")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Error: $cause")
                    }
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "ID no válido")
            }
        }

        // 6. DELETE: LIMPIEZA TOTAL (Eliminación en cascada manual)
        delete("/full/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                try {
                    // Aquí llamamos al nuevo método que crearemos en el Service
                    service.eliminarUsuarioCompleto(id)
                    call.respond(HttpStatusCode.OK, "Usuario $id y todos sus datos asociados eliminados con éxito")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error en la limpieza: ${e.message}")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "ID no válido")
            }
        }

        // 7. GET por EMAIL
        get("/email/{email}") {
            val email = call.parameters["email"]
            if (email == null) {
                call.respond(HttpStatusCode.BadRequest, "Falta email")
                return@get
            }

            val usuario = service.buscarPorEmail(email)
            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            }
        }
    }
}