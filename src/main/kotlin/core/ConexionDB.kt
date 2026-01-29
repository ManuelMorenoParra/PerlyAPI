package edu.gva.es.core

import edu.gva.es.data.Usuarios
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object ConexionDB {

    private const val HOST = "192.168.3.128"
    private const val PORT = 3306
    private const val DATABASE = "proyecto"
    private const val USER = "dam"
    private const val PASSWORD = "Dam2526"

    private val URL =
        "jdbc:mysql://$HOST:$PORT/$DATABASE?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Madrid"

    lateinit var db: Database

    fun conectar() {
        try {
            db = Database.connect(
                url = URL,
                user = USER,
                password = PASSWORD
            )

            println("Conexión establecida con éxito")

            transaction(db) {
                SchemaUtils.create(Usuarios)
                println("Esquema de la tabla 'Usuarios' verificado/creado.")
            }

        } catch (e: Exception) {
            println("Error DB: ${e.message}")
        }
    }
}
