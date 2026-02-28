package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import edu.gva.es.domain.*

object Comentarios : Table("comentarios") {

    val idComentario = integer("id_comentario").autoIncrement()
    val idPublicacion = integer("id_publicacion").references(Publicaciones.id)
    val idUsuario = integer("id_usuario").references(Usuarios.id)
    val contenido = text("texto") 
    val fecha = datetime("fecha")

    override val primaryKey = PrimaryKey(idComentario)
}
