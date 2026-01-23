package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Soporte : Table("soporte") {
    val id = integer("id_ticket").autoIncrement()
    val idUsuario = integer("id_usuario")
    val asunto = varchar("asunto", 100)
    val descripcion = text("descripcion")
    val estado = varchar("estado", 20)
    val respuesta = text("respuesta").nullable()
    val fechaApertura = datetime("fecha_apertura")
    val fechaRespuesta = datetime("fecha_respuesta").nullable()

    override val primaryKey = PrimaryKey(id)
}
