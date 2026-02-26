package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object Seguidores : Table("seguidores") {
    val idSeguimiento = integer("id_seguimiento").autoIncrement()
    val idSeguidor = integer("id_seguidor").references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val idSeguido = integer("id_seguido").references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val fechaSeguimiento = datetime("fecha_seguimiento")

    override val primaryKey = PrimaryKey(idSeguimiento)

    init {
        uniqueIndex("idx_seguimiento_unico", idSeguidor, idSeguido)
    }
}