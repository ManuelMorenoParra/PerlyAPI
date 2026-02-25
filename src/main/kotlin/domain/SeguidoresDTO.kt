package edu.gva.es.domain

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object SeguidoresDTO : Table("seguidores") {

    val id = integer("id_seguimiento").autoIncrement()
    val idUsuario = integer("id_usuario")
        .references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val idSeguido = integer("id_seguido")
        .references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val fecha = datetime("fecha_seguimiento")
    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("idx_seguimiento_unico", idUsuario, idSeguido)
    }
}