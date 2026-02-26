package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Likes : Table("likes") {
    val id = integer("id").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idPublicacion = integer("id_publicacion")
    // Usamos LocalDateTime de Java para la compatibilidad con exposed-java-time
    val fecha = datetime("fecha").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}