package edu.gva.es

import edu.gva.es.core.ConexionDB
import edu.gva.es.plugins.configureRouting
import edu.gva.es.plugins.configureSecurity
import edu.gva.es.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    println("Iniciando conexión con la base de datos...")
    ConexionDB.conectar()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization() // Asegúrate de que este plugin tenga ignoreUnknownKeys = true
    configureSecurity()
    configureRouting()
}