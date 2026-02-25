package edu.gva.es.data
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object Retos : Table("retos") {
    val id_reto = integer("id_reto").autoIncrement()
    val titulo = varchar("titulo", 100)
    val descripcion = text("descripcion")
    val puntos = integer("puntos")
    override val primaryKey = PrimaryKey(id_reto)
}