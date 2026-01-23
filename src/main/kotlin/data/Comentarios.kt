package data

import org.jetbrains.exposed.sql.Table

object Comentarios : Table("comentarios") {
    val id = integer("id").autoIncrement()
    val idPublicacion = integer("id_publicacion")
    val idUsuario = integer("id_usuario")
    val texto = text("texto")
    val fecha = varchar("fecha", 50)

    override val primaryKey = PrimaryKey(id)
}
