package edu.gva.es.data

import edu.gva.es.domain.PublicacionesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object PublicacionesDAO {

    // Quitamos el transaction{} de aquí porque ya se abre en getAll()
    private fun rowToDto(it: ResultRow): PublicacionesDTO {
        val pubId = it[Publicaciones.id]

        // 1. Contar likes (Seguro)
        val totalLikes = Likes.select { Likes.idPublicacion eq pubId }.count().toInt()

        // 2. Sacar los nombres SIN innerJoin (A prueba de fallos)
        // Paso A: Sacamos la lista de IDs de los usuarios que dieron like
        val idsUsuarios = Likes
            .slice(Likes.idUsuario)
            .select { Likes.idPublicacion eq pubId }
            .map { row -> row[Likes.idUsuario] }

        // Paso B: Buscamos los nombres de esos IDs (Solo si hay likes)
        val usuariosLike = if (idsUsuarios.isNotEmpty()) {
            Usuarios
                .slice(Usuarios.nombre)
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