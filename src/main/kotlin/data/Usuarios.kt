package edu.gva.es.data
import org.jetbrains.exposed.sql.Table

object Usuarios : Table("usuarios") {
    val id = integer("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 50)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
    override val primaryKey = PrimaryKey(id)
}