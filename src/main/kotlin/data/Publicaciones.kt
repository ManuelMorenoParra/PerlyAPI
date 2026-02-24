package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import edu.gva.es.domain.*

object Publicaciones : Table("publicaciones") {

    val id = integer("id_publicacion").autoIncrement()
    val idUsuario = integer("id_usuario")
    val texto = text("texto")
    val fecha = datetime("fecha")
    val imagen = blob("imagen").nullable()

    override val primaryKey = PrimaryKey(id)
}
