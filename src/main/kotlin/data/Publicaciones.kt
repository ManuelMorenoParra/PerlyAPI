package data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Publicaciones : Table("publicaciones") {

    val id = integer("id_publicacion").autoIncrement()
    val idUsuario = integer("id_usuario")
    val texto = text("texto")
    val fecha = datetime("fecha")

    override val primaryKey = PrimaryKey(id)
}
