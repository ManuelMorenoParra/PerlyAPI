package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object Mensajes : Table("mensajes") {

    val id = integer("id_mensaje").autoIncrement()
    val idEmisor = integer("id_emisor").references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val idReceptor = integer("id_receptor").references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val contenido = text("contenido")
    val fecha = datetime("fecha_mensaje")
    val leido = bool("leido").default(false)

    override val primaryKey = PrimaryKey(id)
}