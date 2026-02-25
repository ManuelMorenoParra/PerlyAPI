package edu.gva.es.data
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Progresos : Table("progreso") { // Renombrado a Progresos para que ConexionDB lo encuentre
    val id = integer("id_progreso").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idReto = integer("id_reto")
    val puntosGanados = integer("puntos_ganados")
    val fecha = date("fecha")
    val completado = bool("completado")
    override val primaryKey = PrimaryKey(id)
}