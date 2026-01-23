package edu.gva.es.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json() // Esto indica a Ktor que use kotlinx-serialization para manejar JSON
    }
}