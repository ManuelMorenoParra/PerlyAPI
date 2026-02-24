package edu.gva.es.plugins

import edu.gva.es.domain.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    routing {

        post("/login") { /* ... */ }

        authenticate("auth-session") {
            route("/api/publicaciones") {
                post {
                delete("/{id}") {
            }
        }
    }
}