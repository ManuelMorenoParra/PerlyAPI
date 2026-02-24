package edu.gva.es.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.ReferenceOption

object Progresos : Table("progresos") {

    val id = integer("id_progreso").autoIncrement()
    val idUsuario = integer("id_usuario")
        .references(Usuarios.id, onDelete = ReferenceOption.CASCADE)
    val idReto = integer("id_reto")
        .references(Retos.id, onDelete = ReferenceOption.CASCADE)
    val puntosGanados = integer("puntos_ganados")
    val fecha = datetime("fecha_progreso")
    val completado = bool("completado").default(true)

    override val primaryKey = PrimaryKey(id)
}