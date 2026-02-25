package edu.gva.es.data
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object Progresos : Table("progresos") {
    val id = integer("id_progreso").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idReto = integer("id_reto")
    val puntosGanados = integer("puntos_ganados")
    val fecha = datetime("fecha_progreso") // Asegúrate de usar datetime o date según tu DB
    val completado = bool("completado")
    override val primaryKey = PrimaryKey(id)
}