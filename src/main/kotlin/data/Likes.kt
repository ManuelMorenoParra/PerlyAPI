package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Likes : Table("likes") {
    val id = integer("id_like").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idPublicacion = integer("id_publicacion")
    val fecha = datetime("fecha")

    override val primaryKey = PrimaryKey(id)
}
