package edu.gva.es.plugins

import edu.gva.es.domain.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<UserSession>("USER_SESSION") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 3600 // La sesión expira en 1 hora
        }
    }

    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { session -> session }
            challenge {
                call.respondText("401: No autorizado. Debes iniciar sesión.", status = io.ktor.http.HttpStatusCode.Unauthorized)
            }
        }
    }
}