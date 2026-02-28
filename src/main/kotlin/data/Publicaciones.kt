package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Publicaciones : Table("publicaciones") {
    val id = integer("id").autoIncrement()
    val idUsuario = integer("id_usuario")
    val texto = text("texto")
    val fecha = datetime("fecha")
    val imagen = text("imagen").nullable()
    val idRetoVinculado = integer("id_reto_vinculado").nullable()

    override val primaryKey = PrimaryKey(id)
}