package data

import org.jetbrains.exposed.sql.Table

object Publicaciones : Table("publicaciones") {
    val id = integer("id").autoIncrement()
    val idUsuario = integer("id_usuario")
    val contenido = text("contenido")
    val fecha = varchar("fecha", 50)

    override val primaryKey = PrimaryKey(id)
}
