package edu.gva.es.data

import edu.gva.es.domain.PublicacionesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object PublicacionesDAO {

    private fun rowToDto(it: ResultRow): PublicacionesDTO = transaction {
        val pubId = it[Publicaciones.id]

        // Contar likes
        val totalLikes = Likes.selectAll().where { Likes.idPublicacion eq pubId }.count().toInt()

        // Obtener nombres de usuarios que dieron like
        val usuariosLike = (Likes innerJoin Usuarios)
            .select(Usuarios.nombre)
            .where { Likes.idPublicacion eq pubId }
            .map { row -> row[Usuarios.nombre] }

        PublicacionesDTO(
            id = pubId,
            idUsuario = it[Publicaciones.idUsuario],
            texto = it[Publicaciones.texto],
            fecha = it[Publicaciones.fecha].toString(),
            imagen = it[Publicaciones.imagen],
            idRetoVinculado = it[Publicaciones.idRetoVinculado],
            likesCount = totalLikes,
            likedBy = usuariosLike
        )
    }

    fun getAll(): List<PublicacionesDTO> = transaction {
        Publicaciones.selectAll().orderBy(Publicaciones.fecha to SortOrder.DESC).map { rowToDto(it) }
    }

    fun getByUsuario(idUser: Int): List<PublicacionesDTO> = transaction {
        Publicaciones.selectAll().where { Publicaciones.idUsuario eq idUser }.map { rowToDto(it) }
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