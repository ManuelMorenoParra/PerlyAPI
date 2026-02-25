package edu.gva.es.domain

import edu.gva.es.data.Usuarios
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object BloqueosDTO : Table("bloqueos") {

    val id = integer("id_bloqueo").autoIncrement()
    val idBloqueador = integer("id_bloqueador").references(Usuarios.id)
    val idBloqueado = integer("id_bloqueado").references(Usuarios.id)
    val fecha = datetime("fecha_bloqueo")
    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("idx_bloqueo_unico", idBloqueador, idBloqueado)
    }
}