package data

import domain.PublicacionDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime

object PublicacionesDAO {

    private fun ResultRow.toDTO() = PublicacionDTO(
        id = this[Publicaciones.id],
        idUsuario = this[Publicaciones.idUsuario],
        texto = this[Publicaciones.texto],
        fecha = this[Publicaciones.fecha].toString()
    )

    fun getAll(): List<PublicacionDTO> = transaction {
        Publicaciones.selectAll().map { it.toDTO() }
    }

    fun getByUsuario(idUsuario: Int): List<PublicacionDTO> = transaction {
        Publicaciones
            .select { Publicaciones.idUsuario eq idUsuario }
            .map { it.toDTO() }
    }

    fun insert(pub: PublicacionDTO): Int = transaction {
        Publicaciones.insert {
            it[idUsuario] = pub.idUsuario
            it[texto] = pub.texto
            it[fecha] = LocalDateTime.now()
        } get Publicaciones.id
    }

    fun delete(id: Int): Boolean = transaction {
        Publicaciones.deleteWhere { Publicaciones.id eq id } > 0
    }
}
