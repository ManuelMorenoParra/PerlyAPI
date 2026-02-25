package edu.gva.es.data

import edu.gva.es.domain.PublicacionesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

object PublicacionesDAO {

    fun getAll(): List<PublicacionesDTO> = transaction {
        Publicaciones.selectAll().map { rowToDto(it) }
    }

    fun getByUsuario(idUser: Int): List<PublicacionesDTO> = transaction {
        Publicaciones.selectAll().where { Publicaciones.idUsuario eq idUser }.map { rowToDto(it) }
    }

    fun insert(dto: PublicacionesDTO): Int = transaction {
        Publicaciones.insert {
            it[idUsuario] = dto.idUsuario
            it[texto] = dto.texto
            // it[fecha] = LocalDateTime.now()
            if (dto.imagen != null) {
                it[imagen] = ExposedBlob(dto.imagen)
            }
        } get Publicaciones.id
    }

    fun delete(idPub: Int): Boolean = transaction {
        Publicaciones.deleteWhere { id eq idPub } > 0
    }

    fun update(idPub: Int, dto: PublicacionesDTO): Boolean = transaction {
        Publicaciones.update({ Publicaciones.id eq idPub }) {
            it[texto] = dto.texto
            if (dto.imagen != null) {
                it[imagen] = ExposedBlob(dto.imagen)
            }
        } > 0
    }

    private fun rowToDto(it: ResultRow) = PublicacionesDTO(
        id = it[Publicaciones.id],
        idUsuario = it[Publicaciones.idUsuario],
        texto = it[Publicaciones.texto],
        fecha = it[Publicaciones.fecha].toString(),
        imagen = it[Publicaciones.imagen]?.bytes
    )
}