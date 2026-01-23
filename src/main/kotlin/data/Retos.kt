package data

import org.jetbrains.exposed.sql.Table

object Retos : Table("retos") {
    val id = integer("id").autoIncrement()
    val titulo = varchar("titulo", 100)
    val descripcion = text("descripcion")
    val puntos = integer("puntos")

    override val primaryKey = PrimaryKey(id)
}
