package edu.gva.es.data

import edu.gva.es.domain.PublicacionesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object PublicacionesDAO {

    private fun rowToDto(it: ResultRow): PublicacionesDTO {
        val pubId = it[Publicaciones.id]

        // 1. Los comentarios ya vienen con su nombre de usuario gracias al JOIN en su propio DAO
        val listaComentarios = ComentariosDAO.obtenerPorPublicacion(pubId)

        // 2. Lógica de Likes optimizada (Sin abrir nuevas transacciones innecesarias)
        val totalLikes = Likes.select { Likes.idPublicacion eq pubId }.count().toInt()

        val usuariosLike = if (totalLikes > 0) {
            val idsUsuarios = Likes.slice(Likes.idUsuario)
                .select { Likes.idPublicacion eq pubId }
                .map { row -> row[Likes.idUsuario] }

            Usuarios.slice(Usuarios.nombre)
                .select { Usuarios.id inList idsUsuarios }
                .map { row -> row[Usuarios.nombre] }
        } else {
            emptyList()
        }

        return PublicacionesDTO(
            id = pubId,
            idUsuario = it[Publicaciones.idUsuario],
            texto = it[Publicaciones.texto],
            fecha = it[Publicaciones.fecha].toString(),
            imagen = it[Publicaciones.imagen],
            // Estos campos funcionan porque getAll() hace el INNER JOIN con Usuarios
            nombreUsuario = it[Usuarios.nombre],
            avatarUsuario = it[Usuarios.avatar],
            likesCount = totalLikes,
            likedBy = usuariosLike,
            comments = listaComentarios,
            idRetoVinculado = it.getOrNull(Publicaciones.idRetoVinculado)
        )
    }

    fun getAll(): List<PublicacionesDTO> = transaction {
        // Añadimos logging para ver qué falla en consola si persiste el error
        addLogger(StdOutSqlLogger)

        (Publicaciones innerJoin Usuarios)
            .selectAll()
            .orderBy(Publicaciones.fecha to SortOrder.DESC)
            .map { rowToDto(it) }
    }

    fun getByUsuario(idUser: Int): List<PublicacionesDTO> = transaction {
        (Publicaciones innerJoin Usuarios)
            .select { Publicaciones.idUsuario eq idUser }
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