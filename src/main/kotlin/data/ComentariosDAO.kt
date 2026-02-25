package edu.gva.es.data

import edu.gva.es.domain.ComentarioDTO // Import corregido
import edu.gva.es.domain.Comentarios
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object ComentariosDAO {
    fun insertar(dto: ComentarioDTO): Int = transaction {
        Comentarios.insert {
            it[idPublicacion] = dto.idPublicacion
            it[idUsuario] = dto.idUsuario
            it[contenido] = dto.contenido
        } get Comentarios.id
    }

    fun obtenerPorPublicacion(idPub: Int): List<ComentarioDTO> = transaction {
        Comentarios.selectAll().where { Comentarios.idPublicacion eq idPub }.map {
            ComentarioDTO(
                id = it[Comentarios.id],
                idPublicacion = it[Comentarios.idPublicacion],
                idUsuario = it[Comentarios.idUsuario],
                contenido = it[Comentarios.contenido]
            )
        }
    }

    fun actualizar(idCom: Int, dto: ComentarioDTO): Boolean = transaction {
        Comentarios.update({ Comentarios.id eq idCom }) {
            it[contenido] = dto.contenido
        } > 0
    }

    fun eliminar(idCom: Int): Boolean = transaction {
        Comentarios.deleteWhere { id eq idCom } > 0
    }
}