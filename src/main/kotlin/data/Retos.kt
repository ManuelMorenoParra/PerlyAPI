package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import edu.gva.es.domain.*

object Retos : Table("retos") {
    val id_reto = integer("id_reto").autoIncrement() // coincide con la columna en la DB
    val titulo = varchar("titulo", 100)
    val descripcion = text("descripcion")
    val puntos = integer("puntos")

    override val primaryKey = PrimaryKey(id_reto)
}
