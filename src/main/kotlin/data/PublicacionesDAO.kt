package edu.gva.es.data

import edu.gva.es.domain.PublicacionDTO // Import corregido
import edu.gva.es.domain.Publicaciones
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object PublicacionesDAO {
    fun insertar(dto: PublicacionDTO): Int = transaction {
        Publicaciones.insert {
            it[idUsuario] = dto.idUsuario
            it[texto] = dto.texto
            it[imagenBase64] = dto.imagenBase64
        } get Publicaciones.id
    }

    fun obtenerTodas(): List<PublicacionDTO> = transaction {
        Publicaciones.selectAll().map { rowToDto(it) }
    }

    fun obtenerPorUsuario(idUser: Int): List<PublicacionDTO> = transaction {
        Publicaciones.selectAll().where { Publicaciones.idUsuario eq idUser }.map { rowToDto(it) }
    }

    private fun rowToDto(it: ResultRow) = PublicacionDTO(
        id = it[Publicaciones.id],
        idUsuario = it[Publicaciones.idUsuario],
        texto = it[Publicaciones.texto],
        imagenBase64 = it[Publicaciones.imagenBase64]
    )

    fun actualizar(idPub: Int, dto: PublicacionDTO): Boolean = transaction {
        Publicaciones.update({ Publicaciones.id eq idPub }) {
            it[texto] = dto.texto
            it[imagenBase64] = dto.imagenBase64
        } > 0
    }

    fun eliminar(idPub: Int): Boolean = transaction {
        Publicaciones.deleteWhere { id eq idPub } > 0
    }
}