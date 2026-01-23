package edu.gva.es.data

import edu.gva.es.domain.LikeDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object LikesDAO {

    fun insertar(like: LikeDTO) = transaction {
        Likes.insert {
            it[idUsuario] = like.idUsuario
            it[idPublicacion] = like.idPublicacion
            it[fecha] = LocalDateTime.now()
        }
    }

    fun eliminar(idUsuario: Int, idPublicacion: Int) = transaction {
        Likes.deleteWhere {
            (Likes.idUsuario eq idUsuario) and
                    (Likes.idPublicacion eq idPublicacion)
        }
    }

    fun contarLikes(idPublicacion: Int) = transaction {
        Likes.selectAll()
            .where { Likes.idPublicacion eq idPublicacion }
            .count()
    }
}
