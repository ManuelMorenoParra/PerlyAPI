package edu.gva.es.domain

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object Likes : Table("likes") {

    val idUsuario = integer("id_usuario")
        .references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val idPublicacion = integer("id_publicacion")
        .references(Publicaciones.id, onDelete = ReferenceOption.CASCADE)
    val fecha = datetime("fecha_like")

    override val primaryKey = PrimaryKey(idUsuario, idPublicacion)
}