package edu.gva.es.core

object ConexionDB {

    private const val HOST = "127.0.0.1"
    private const val PORT = 3306
    private const val DATABASE = "proyecto"
    private const val USER = "dam"
    private const val PASSWORD = "Dam2526"

    private val URL = "jdbc:mysql://$HOST:$PORT/$DATABASE?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Madrid"

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