package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Bloqueos : Table("bloqueos") {
    val id = integer("id_bloqueo").autoIncrement()
    // Referenciamos id_usuario de la tabla Usuarios
    val idBloqueador = integer("id_usuario_bloqueador").references(Usuarios.idUsuario)
    val idBloqueado = integer("id_usuario_bloqueado").references(Usuarios.idUsuario)

    override val primaryKey = PrimaryKey(id)
}