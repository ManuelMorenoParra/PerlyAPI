package edu.gva.es.domain

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object Publicaciones : Table("publicaciones") {

    val id = integer("id_publicacion").autoIncrement()
    val idUsuario = integer("id_usuario")
        .references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val texto = text("texto")
    val fecha = datetime("fecha_publicacion")
    val imagen = blob("imagen").nullable()

    override val primaryKey = PrimaryKey(id)
}