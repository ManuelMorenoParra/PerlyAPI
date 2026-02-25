package edu.gva.es.data

import org.jetbrains.exposed.sql.Table

object Retos : Table("retos") {
    val id = integer("id").autoIncrement() // Según script SQL es "id"
    val titulo = varchar("titulo", 255)
    val descripcion = text("descripcion")
    val categoria = varchar("categoria", 50) // 'mental', 'physical', etc.
    val puntos = integer("puntos").default(0)
    val esDiario = bool("es_diario").default(false)

    override val primaryKey = PrimaryKey(id)
}