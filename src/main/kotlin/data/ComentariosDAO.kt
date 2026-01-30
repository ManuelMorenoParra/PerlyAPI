package edu.gva.es.data

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import domain.ComentarioDTO
import java.time.LocalDateTime

object ComentariosDAO {

    private fun ResultRow.toDTO() = ComentarioDTO(
        id = this[Comentarios.idComentario],
        idPublicacion = this[Comentarios.idPublicacion],
        idUsuario = this[Comentarios.idUsuario],
        texto = this[Comentarios.texto],
        fecha = this[Comentarios.fecha].toString()
    )

    fun getByPublicacion(idPub: Int): List<ComentarioDTO> = transaction {
        Comentarios
            .select { Comentarios.idPublicacion eq idPub }
            .map { it.toDTO() }
    }

    fun insert(c: ComentarioDTO): Int = transaction {
        Comentarios.insert {
            it[idPublicacion] = c.idPublicacion
            it[idUsuario] = c.idUsuario
            it[texto] = c.texto
            it[fecha] = LocalDateTime.now()
        } get Comentarios.idComentario
    }

    fun delete(id: Int): Boolean = transaction {
        Comentarios.deleteWhere { Comentarios.idComentario eq id } > 0
    }
}

