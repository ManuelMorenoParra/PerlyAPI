package data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Progreso : Table("progreso") {

    val id = integer("id_progreso").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idReto = integer("id_reto")

    val puntosGanados = integer("puntos_ganados")

    val fecha = date("fecha")

    val completado = bool("completado")

    override val primaryKey = PrimaryKey(id)
}