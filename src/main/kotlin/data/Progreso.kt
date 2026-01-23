package data

import org.jetbrains.exposed.sql.Table

object Progreso : Table("progreso") {
    val id = integer("id").autoIncrement()
    val idUsuario = integer("id_usuario")
    val idReto = integer("id_reto")
    val puntosGanados = integer("puntos_ganados")
    val fecha = varchar("fecha", 50)
    val completado = bool("completado")

    override val primaryKey = PrimaryKey(id)
}
