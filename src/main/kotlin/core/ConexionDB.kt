package edu.gva.es.core

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import edu.gva.es.data.*

object ConexionDB {

    private const val HOST = "pearly.cch8km4gcrmo.us-east-1.rds.amazonaws.com"
    private const val PORT = 3306
    private const val DATABASE = "pearlyDB"
    private const val USER = "Administrator"
    private const val PASSWORD = "PI2026dam"

    private val URL =
        "jdbc:mysql://$HOST:$PORT/$DATABASE" +
                "?useSSL=false" +
                "&allowPublicKeyRetrieval=true" +
                "&serverTimezone=Europe/Madrid"


    lateinit var db: Database

    fun conectar() {
        try {
            Database.connect(URL, driver = "com.mysql.cj.jdbc.Driver", user = USER, password = PASSWORD)

            transaction {

                SchemaUtils.create(
                    Usuarios, Publicaciones, Comentarios,
                    Likes, Seguidores, Soportes,
                    Retos, Progresos
                )
            }
            println("DB Conectada y Esquema sincronizado.")

        } catch (e: Exception) {
            println("Error crítico en DB: ${e.message}")
        }
    }
}