package edu.gva.es.plugins

import edu.gva.es.domain.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {

    // 1. Configuración de Sesiones
    install(Sessions) {
        cookie<UserSession>("USER_SESSION") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 3600
        }
    }

    // 2. Configuración de Autenticación
    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { session ->
                session // Aquí podrías añadir lógica de validación extra
            }
            challenge {
                call.respondText("No tienes permiso para acceder a esto.", status = io.ktor.http.HttpStatusCode.Unauthorized)
            }
        }
    }

    // 3. Definición de Rutas
    routing {
        post("/login") {
            /* Lógica de login aquí */
        }

        // Bloque protegido
        authenticate("auth-session") {
            route("/api/publicaciones") {
                post {
                    call.respondText("Publicación creada")
                } // Cierre del post

                delete("/{id}") {
                    call.respondText("Publicación eliminada")
                } // Cierre del delete
            } // Cierre del route
        } // Cierre del authenticate
    }
}