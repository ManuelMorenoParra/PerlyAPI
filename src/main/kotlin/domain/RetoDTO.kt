package edu.gva.es.domain

import org.jetbrains.exposed.sql.Table

object Retos : Table("retos") {

    val id = integer("id_reto").autoIncrement()
    val titulo = varchar("titulo", 100).uniqueIndex()
    val descripcion = text("descripcion")
    val puntos = integer("puntos_recompensa")

    override val primaryKey = PrimaryKey(id)
}