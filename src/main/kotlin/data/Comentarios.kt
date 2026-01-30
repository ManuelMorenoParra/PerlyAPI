package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Comentarios : Table("comentarios") {

    val idComentario = integer("id_comentario").autoIncrement()
    val idPublicacion = integer("id_publicacion")
    val idUsuario = integer("id_usuario")
    val texto = text("texto")
    val fecha = datetime("fecha")

    override val primaryKey = PrimaryKey(idComentario)
}
