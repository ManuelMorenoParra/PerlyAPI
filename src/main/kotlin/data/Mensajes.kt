package data

import org.jetbrains.exposed.sql.Table

object Mensajes : Table("mensajes") {
    val id = integer("id").autoIncrement()
    val idEmisor = integer("id_emisor")
    val idReceptor = integer("id_receptor")
    val mensaje = text("mensaje")
    val fecha = varchar("fecha", 50)
    val leido = bool("leido")

    override val primaryKey = PrimaryKey(id)
}
