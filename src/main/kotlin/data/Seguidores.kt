package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Seguidores : Table("seguidores") {
    val id = integer("id_seguidor").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idSeguido = integer("id_seguido")
    val fecha = datetime("fecha")

    override val primaryKey = PrimaryKey(id)
}
