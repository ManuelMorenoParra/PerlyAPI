package data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Mensajes : Table("mensajes") {
    val id = integer("id_mensaje").autoIncrement()
    val idEmisor = integer("id_emisor")
    val idReceptor = integer("id_receptor")
    val mensaje = text("mensaje")
    val fecha = datetime("fecha")
    val leido = bool("leido").default(false)

    override val primaryKey = PrimaryKey(id)
}
