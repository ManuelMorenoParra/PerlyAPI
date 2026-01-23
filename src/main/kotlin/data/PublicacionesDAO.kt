package data

import domain.PublicacionDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PublicacionesDAO {

    fun getAll(): List<PublicacionDTO> = transaction {
        Publicaciones.selectAll().map {
            PublicacionDTO(
                id = it[Publicaciones.id],
                idUsuario = it[Publicaciones.idUsuario],
                contenido = it[Publicaciones.contenido],
                fecha = it[Publicaciones.fecha]
            )
        }
    }

    fun insert(pub: PublicacionDTO): Int = transaction {
        Publicaciones.insert {
            it[idUsuario] = pub.idUsuario
            it[contenido] = pub.contenido
            it[fecha] = java.time.LocalDateTime.now().toString()
        } get Publicaciones.id
    }

    fun delete(id: Int): Boolean = transaction {
        Publicaciones.deleteWhere { Publicaciones.id eq id } > 0
    }
}
