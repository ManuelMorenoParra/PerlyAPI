package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Bloqueos : Table("bloqueos") {
    val id = integer("id_bloqueo").autoIncrement()
    val idBloqueador = integer("id_usuario_bloqueador").references(Usuarios.idUsuario)
    val idBloqueado = integer("id_usuario_bloqueado").references(Usuarios.idUsuario)
    val fecha = datetime("fecha_bloqueo")

    override val primaryKey = PrimaryKey(id)
}