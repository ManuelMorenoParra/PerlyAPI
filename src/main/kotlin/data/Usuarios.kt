package edu.gva.es.data
import org.jetbrains.exposed.sql.Table

object Usuarios : Table("usuarios") {
    val id = integer("id").autoIncrement() // Según tu SQL es "id"
    val nombre = varchar("nombre", 100)
    val email = varchar("email", 150).uniqueIndex()
    val password = varchar("password", 255)
    val bio = text("bio").nullable()
    val avatar = text("avatar").nullable() // LONGTEXT en SQL se mapea como text()
    val puntosTotales = integer("puntos_totales").default(0)
    val puntosEnergia = integer("puntos_energia").default(0)
    val rachaActual = integer("racha_actual").default(0)

    override val primaryKey = PrimaryKey(id)
}