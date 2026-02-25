package edu.gva.es.plugins

import edu.gva.es.domain.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<UserSession>("USER_SESSION") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 3600
        }
    }

    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { session ->
                session
            }
            challenge {
                call.respondText("No tienes permiso para acceder a esto.", status = io.ktor.http.HttpStatusCode.Unauthorized)
            }
        }
    }

    routing {
        post("/login") {

        }

        authenticate("auth-session") {
            route("/api/publicaciones") {
                post {
                    call.respondText("Publicación creada")
                }

                delete("/{id}") {
                    call.respondText("Publicación eliminada")
                }
            }
        }
    }
}