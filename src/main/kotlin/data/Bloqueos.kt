package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Bloqueos : Table("bloqueos") {
    val id = integer("id").autoIncrement()
    val idBloqueador = integer("id_bloqueador") // El usuario que ejecuta la acción
    val idBloqueado = integer("id_bloqueado")   // El usuario que es restringido
    val tipo = varchar("tipo", 10).default("block") // 'block' o 'mute'
    val fecha = datetime("fecha_bloqueo")

    override val primaryKey = PrimaryKey(id)
}