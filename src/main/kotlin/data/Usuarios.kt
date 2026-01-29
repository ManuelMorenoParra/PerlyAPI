package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Usuarios : Table("usuarios") {

    val idUsuario = integer("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 50)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
    val fechaNacimiento = date("fecha_nacimiento").nullable()

    override val primaryKey = PrimaryKey(idUsuario)
}
