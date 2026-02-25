package edu.gva.es

import edu.gva.es.core.ConexionDB
import edu.gva.es.data.*
import edu.gva.es.domain.*
import edu.gva.es.plugins.configureRouting
import edu.gva.es.plugins.configureSecurity
import edu.gva.es.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    println("Iniciando PearlyAPI...")

    ConexionDB.conectar()

    transaction {
        SchemaUtils.create(
            Usuarios,
            Publicaciones,
            Comentarios,
            Likes,
            Seguidores,
            Soportes,
            Retos,
            Progresos,
            Bloqueos
        )
    }

    println("Base de datos sincronizada correctamente")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureRouting()
}