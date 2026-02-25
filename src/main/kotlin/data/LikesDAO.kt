package edu.gva.es.data

import edu.gva.es.domain.LikeDTO
import edu.gva.es.domain.Likes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object LikesDAO {
    fun insertar(dto: LikeDTO): Boolean = transaction {
        Likes.insert {
            it[idUsuario] = dto.idUsuario
            it[idPublicacion] = dto.idPublicacion
        }.insertedCount > 0
    }

    fun eliminar(u: Int, p: Int): Boolean = transaction {
        Likes.deleteWhere { (idUsuario eq u) and (idPublicacion eq p) } > 0
    }

    fun contarLikes(p: Int): Long = transaction {
        Likes.selectAll().where { Likes.idPublicacion eq p }.count()
    }

    fun update(idLike: Int, dto: LikeDTO): Boolean = transaction {
        Likes.update({ Likes.id eq idLike }) {
            it[idUsuario] = dto.idUsuario
            it[idPublicacion] = dto.idPublicacion
        } > 0
    }
}