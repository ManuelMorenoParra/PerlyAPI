package edu.gva.es.domain

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object Soportes : Table("soportes") {

    val id = integer("id_ticket").autoIncrement()
    val idUsuario = integer("id_usuario")
        .references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val asunto = varchar("asunto", 150)
    val descripcion = text("descripcion")
    val estado = varchar("estado", 20).default("ABIERTO")
    val respuesta = text("respuesta").nullable()
    val fechaApertura = datetime("fecha_apertura")
    val fechaRespuesta = datetime("fecha_respuesta").nullable()

    override val primaryKey = PrimaryKey(id)
}