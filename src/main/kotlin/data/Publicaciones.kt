package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Publicaciones : Table("publicaciones") {
    val id = integer("id").autoIncrement() // Ajustado a "id" según el script SQL
    val idUsuario = integer("id_usuario")
    val texto = text("texto")
    val fecha = datetime("fecha")
    val imagen = text("imagen").nullable() // Cambiado de blob a text para Base64
    val idRetoVinculado = integer("id_reto_vinculado").nullable() // Nuevo atributo

    override val primaryKey = PrimaryKey(id)
}