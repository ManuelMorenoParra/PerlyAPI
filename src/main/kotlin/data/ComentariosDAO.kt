package edu.gva.es.data

import edu.gva.es.domain.ComentariosDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object ComentariosDAO {
    fun insertar(dto: ComentariosDTO): Int = transaction {
        Comentarios.insert {
            it[idPublicacion] = dto.idPublicacion
            it[idUsuario] = dto.idUsuario
            it[contenido] = dto.contenido
            it[fecha] = java.time.LocalDateTime.now() 
        } get Comentarios.idComentario
    }

    fun obtenerPorPublicacion(idPub: Int): List<ComentariosDTO> = transaction {
        Comentarios.selectAll().where { Comentarios.idPublicacion eq idPub }.map {
            // CORRECCIÓN: Aquí devolvemos el DTO, no la clase de la Tabla
            ComentariosDTO(
                id = it[Comentarios.idComentario],
                idPublicacion = it[Comentarios.idPublicacion],
                idUsuario = it[Comentarios.idUsuario],
                contenido = it[Comentarios.contenido]
            )
        }
    }

    fun actualizar(idCom: Int, dto: ComentariosDTO): Boolean = transaction {
        Comentarios.update({ Comentarios.idComentario eq idCom }) {
            it[contenido] = dto.contenido
        } > 0
    }

    fun eliminar(idCom: Int): Boolean = transaction {

        Comentarios.deleteWhere { idComentario eq idCom } > 0
    }
}