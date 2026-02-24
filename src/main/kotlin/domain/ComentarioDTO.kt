package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object Comentarios : Table("comentarios") {

    val id = integer("id_comentario").autoIncrement()
    val idPublicacion = integer("id_publicacion")
        .references(Publicaciones.id, onDelete = ReferenceOption.CASCADE)
    val idUsuario = integer("id_usuario")
        .references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val contenido = text("contenido")
    val fecha = datetime("fecha_comentario")

    override val primaryKey = PrimaryKey(id)
}