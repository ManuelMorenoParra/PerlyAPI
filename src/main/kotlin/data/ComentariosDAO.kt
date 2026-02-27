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
                it[fecha] = java.time.LocalDateTime.now() // Fecha automática
            } get Comentarios.idComentario
        }

    fun obtenerPorPublicacion(idPub: Int): List<ComentariosDTO> = transaction {
            // Hacemos JOIN con Usuarios para obtener nombre y avatar
            (Comentarios innerJoin Usuarios)
                .slice(Comentarios.columns + Usuarios.nombre + Usuarios.avatar)
                .select { Comentarios.idPublicacion eq idPub }
                .map {
                    ComentariosDTO(
                        id = it[Comentarios.idComentario],
                        idPublicacion = it[Comentarios.idPublicacion],
                        idUsuario = it[Comentarios.idUsuario],
                        contenido = it[Comentarios.contenido],
                        fecha = it[Comentarios.fecha].toString(),
                        nombreUsuario = it[Usuarios.nombre],
                        avatarUsuario = it[Usuarios.avatar]
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