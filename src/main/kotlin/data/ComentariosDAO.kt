package data

import domain.ComentarioDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ComentariosDAO {

    fun getByPublicacion(idPublicacion: Int): List<ComentarioDTO> = transaction {
        Comentarios.select { Comentarios.idPublicacion eq idPublicacion }
            .map {
                ComentarioDTO(
                    id = it[Comentarios.id],
                    idPublicacion = it[Comentarios.idPublicacion],
                    idUsuario = it[Comentarios.idUsuario],
                    texto = it[Comentarios.texto],
                    fecha = it[Comentarios.fecha]
                )
            }
    }

    fun insert(comentario: ComentarioDTO): Int = transaction {
        Comentarios.insert {
            it[idPublicacion] = comentario.idPublicacion
            it[idUsuario] = comentario.idUsuario
            it[texto] = comentario.texto
            it[fecha] = java.time.LocalDateTime.now().toString()
        } get Comentarios.id
    }

    fun delete(id: Int): Boolean = transaction {
        Comentarios.deleteWhere { Comentarios.id eq id } > 0
    }
}
