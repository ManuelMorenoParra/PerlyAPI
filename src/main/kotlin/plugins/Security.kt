package edu.gva.es.plugins

import edu.gva.es.data.UsuariosDAO
import edu.gva.es.domain.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.*

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<UserSession>("USER_SESSION") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 3600
            cookie.httpOnly = true
            cookie.secure = false
        }
    }

    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { session -> session }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Sesión inválida o expirada.")
            }
        }
    }

    routing {
        post("/login") {
            val params = call.receiveParameters()
            val email = params["email"] ?: ""
            val pass = params["password"] ?: ""

            val usuario = UsuariosDAO.verificarPassword(email, pass)

            if (usuario != null) {

                call.sessions.set(UserSession(id = usuario.id ?: 0, nombre = usuario.nombre))
                call.respond(HttpStatusCode.OK, "Login correcto. Bienvenido ${usuario.nombre}")
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Email o contraseña incorrectos.")
            }
        }

        authenticate("auth-session") {
            route("/api/publicaciones") {
                post {
                    val session = call.sessions.get<UserSession>()

                    call.respondText("Publicación creada por el usuario ID: ${session?.id}")
                }
            }
        }
    }
}