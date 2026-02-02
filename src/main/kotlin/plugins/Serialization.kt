package edu.gva.es.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true // Esto evita el error 400 si envías campos de más
            explicitNulls = false    // Esto permite que "id": null o "fecha": null funcionen
            encodeDefaults = true
        })
    }
}