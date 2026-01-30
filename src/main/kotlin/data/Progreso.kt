package data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Progreso : Table("progreso") {
    // CAMBIO: El nombre en la BD es "id_progreso"
    val id = integer("id_progreso").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idReto = integer("id_reto")

    // NOTA: Asegúrate de que esta columna exista en tu BD,
    // no aparecía en tu captura de pantalla.
    val puntosGanados = integer("puntos_ganados")

    // CAMBIO: Usamos .date() porque en tu captura el tipo es "date"
    val fecha = date("fecha")

    val completado = bool("completado")

    override val primaryKey = PrimaryKey(id)
}