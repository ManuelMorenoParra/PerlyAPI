package edu.gva.es.routes

import edu.gva.es.services.UsuariosService
import edu.gva.es.domain.UsuarioDTO
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.*
import edu.gva.es.domain.UserSession
import edu.gva.es.domain.LoginRequest

fun Route.usuarioRouting() {

    val service = UsuariosService

    // Bloque de autenticación
    route("/auth") {

        // Login
        post("/login") {
            val login = call.receive<LoginRequest>()
            val correcto = service.login(login.email, login.password)
            if (correcto) {
                call.sessions.set(UserSession(email = login.email))
                call.respondText("Login exitoso")
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Credenciales incorrectas")
            }
        }

        // Logout
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
                ?: return@get call.respondText("ID no válido", status = HttpStatusCode.BadRequest)

            val usuario = service.buscarPorId(id)

            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // 3. POST: Crear nuevo usuario
        post {
            try {
                val usuario = call.receive<UsuarioDTO>()
                val nuevoId = service.registrarUsuario(usuario)

                if (nuevoId != -1)
                    call.respond(HttpStatusCode.Created, nuevoId)
                else
                    call.respond(HttpStatusCode.Conflict, "El email ya existe")

            } catch (e: Exception) {
                call.respondText(
                    "Error en los datos: ${e.message}",
                    status = HttpStatusCode.BadRequest
                )
            }
        }

        // 4. PUT: Actualizar usuario
        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respondText("ID no válido", status = HttpStatusCode.BadRequest)

            val usuarioActualizado = call.receive<UsuarioDTO>()
            val filas = service.actualizarUsuario(id, usuarioActualizado)

            if (filas > 0) {
                call.respondText("Usuario actualizado correctamente")
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // 5. DELETE: Eliminar usuario
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("ID no válido", status = HttpStatusCode.BadRequest)

            val eliminado = service.borrarUsuario(id)

            if (eliminado > 0) {
                call.respondText("Usuario eliminado", status = HttpStatusCode.OK)
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // 6. GET por EMAIL (ruta correcta)
        get("/email/{email}") {

            val email = call.parameters["email"]
                ?: return@get call.respondText("Falta email", status = HttpStatusCode.BadRequest)

            val usuario = service.buscarPorEmail(email)

            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // 7. LOGIN
        post("/login") {

            val datos = call.receive<Map<String, String>>()

            val email = datos["email"]
            val password = datos["password"]

            if (email == null || password == null) {
                return@post call.respondText(
                    "Faltan datos",
                    status = HttpStatusCode.BadRequest
                )
            }

            val correcto = service.login(email, password)

            if (correcto)
                call.respondText("Login correcto", status = HttpStatusCode.OK)
            else
                call.respondText("Credenciales incorrectas", status = HttpStatusCode.Unauthorized)
        }
    }
}
