package edu.gva.es.data

import edu.gva.es.domain.LikesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object LikesDAO {
    fun insertar(dto: LikesDTO): Boolean = transaction {
        Likes.insert {
            it[idUsuario] = dto.idUsuario
            it[idPublicacion] = dto.idPublicacion
            // La fecha se pone sola por el defaultExpression, o puedes forzarla:
            it[fecha] = LocalDateTime.now()
        }.insertedCount > 0
    }

    fun eliminar(u: Int, p: Int): Boolean = transaction {
        Likes.deleteWhere { (idUsuario eq u) and (idPublicacion eq p) } > 0
    }

    fun contarLikes(p: Int): Long = transaction {
        Likes.selectAll().where { Likes.idPublicacion eq p }.count()
    }

    fun update(idLike: Int, dto: LikesDTO): Boolean = transaction {
        Likes.update({ Likes.id eq idLike }) {
            it[idUsuario] = dto.idUsuario
            it[idPublicacion] = dto.idPublicacion
            // Si quieres actualizar la fecha al editar:
            it[fecha] = LocalDateTime.now()
        } > 0
    }
}