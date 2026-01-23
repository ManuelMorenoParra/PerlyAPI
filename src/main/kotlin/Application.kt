package edu.gva.es

import edu.gva.es.core.ConexionDB
import edu.gva.es.plugins.configureRouting
import edu.gva.es.plugins.configureSecurity
import edu.gva.es.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    println("Iniciando conexión con la base de datos...")
    ConexionDB.conectar()

    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureRouting()
}
