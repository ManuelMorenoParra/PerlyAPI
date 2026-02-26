package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Likes : Table("likes") {
    val id = integer("id").autoIncrement() // Coincide con SQL
    val idUsuario = integer("id_usuario")
    val idPublicacion = integer("id_publicacion")
    val fecha = datetime("fecha_like").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentDateTime) // Coincide con SQL
    
    override val primaryKey = PrimaryKey(id)
}