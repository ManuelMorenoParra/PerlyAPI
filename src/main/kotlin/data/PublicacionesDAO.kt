package edu.gva.es.data

import edu.gva.es.domain.PublicacionesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object PublicacionesDAO {

    private fun rowToDto(it: ResultRow): PublicacionesDTO {
        val pubId = it[Publicaciones.id]

        // Obtenemos comentarios (asegúrate que ComentariosDAO use el JOIN con Usuarios internamente)
        val listaComentarios = ComentariosDAO.obtenerPorPublicacion(pubId)

        // Contar likes de forma directa
        val totalLikes = Likes.select { Likes.idPublicacion eq pubId }.count().toInt()

        // Obtener nombres de quienes dieron like
        val usuariosLike = Likes
            .slice(Likes.idUsuario)
            .select { Likes.idPublicacion eq pubId }
            .map { row -> 
                Usuarios.slice(Usuarios.nombre)
                    .select { Usuarios.id eq row[Likes.idUsuario] }
                    .map { u -> u[Usuarios.nombre] }.firstOrNull() ?: "Usuario"
            }

        return PublicacionesDTO(
            id = pubId,
            idUsuario = it[Publicaciones.idUsuario],
            texto = it[Publicaciones.texto],
            fecha = it[Publicaciones.fecha].toString(),
            imagen = it[Publicaciones.imagen],
            nombreUsuario = it[Usuarios.nombre], // Viene del JOIN en getAll
            avatarUsuario = it[Usuarios.avatar],
            likesCount = totalLikes,
            likedBy = usuariosLike,
            comments = listaComentarios
        )
    }

    fun getAll(): List<PublicacionesDTO> = transaction {
        (Publicaciones innerJoin Usuarios)
            .selectAll()
            .orderBy(Publicaciones.fecha to SortOrder.DESC)
            .map { rowToDto(it) }
    }

    fun insert(dto: PublicacionesDTO): Int = transaction {
        Publicaciones.insert {
            it[idUsuario] = dto.idUsuario
            it[texto] = dto.texto
            it[fecha] = LocalDateTime.now()
            it[imagen] = dto.imagen
            it[idRetoVinculado] = dto.idRetoVinculado
        } get Publicaciones.id
    }

    fun update(idPub: Int, dto: PublicacionesDTO): Boolean = transaction {
        Publicaciones.update({ Publicaciones.id eq idPub }) {
            it[texto] = dto.texto
            it[imagen] = dto.imagen
            it[idRetoVinculado] = dto.idRetoVinculado
        } > 0
    }

    fun delete(idPub: Int): Boolean = transaction {
        Publicaciones.deleteWhere { id eq idPub } > 0
    }
}