package edu.gva.es.data

import edu.gva.es.domain.PublicacionesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object PublicacionesDAO {

    private fun rowToDto(it: ResultRow): PublicacionesDTO {
        val pubId = it[Publicaciones.id]
        val autorId = it[Publicaciones.idUsuario] 

        // 1. 🌟 MAGIA: Al haber hecho el JOIN, el nombre y el avatar ya vienen 
        // integrados en la misma fila ("it"). Adiós al bloqueo de la base de datos.
        val nombreDelAutor = it[Usuarios.nombre]
        val avatarDelAutor = it[Usuarios.avatar]

        // 2. Contar likes (Seguro)
        val totalLikes = Likes.select { Likes.idPublicacion eq pubId }.count().toInt()

        // 3. Nombres de likes
        val idsUsuarios = Likes.slice(Likes.idUsuario).select { Likes.idPublicacion eq pubId }.map { row -> row[Likes.idUsuario] }
        val usuariosLike = if (idsUsuarios.isNotEmpty()) {
            Usuarios.slice(Usuarios.nombre).select { Usuarios.id inList idsUsuarios }.map { row -> row[Usuarios.nombre] }
        } else { emptyList() }

        val listaComentarios = ComentariosDAO.obtenerPorPublicacion(pubId)

        return PublicacionesDTO(
            id = pubId,
            idUsuario = autorId,
            texto = it[Publicaciones.texto],
            fecha = it[Publicaciones.fecha].toString(),
            imagen = it[Publicaciones.imagen],
            idRetoVinculado = it[Publicaciones.idRetoVinculado],
            likesCount = totalLikes,
            likedBy = usuariosLike,
            nombreUsuario = nombreDelAutor, // 👈 Ahora sí que sí, enviamos el real
            avatarUsuario = avatarDelAutor,
            comments = listaComentarios
        )
    }

    fun getAll(): List<PublicacionesDTO> = transaction {
        // 🌟 Usamos "join" directamente para traer toda la info cruzada en 1 sola consulta
        Publicaciones.join(Usuarios, JoinType.INNER, additionalConstraint = { Publicaciones.idUsuario eq Usuarios.id })
            .selectAll()
            .orderBy(Publicaciones.fecha to SortOrder.DESC)
            .map { rowToDto(it) }
    }

    fun getByUsuario(idUser: Int): List<PublicacionesDTO> = transaction {
        // 🌟 Usamos el mismo "join" aquí para el perfil
        Publicaciones.join(Usuarios, JoinType.INNER, additionalConstraint = { Publicaciones.idUsuario eq Usuarios.id })
            .selectAll()
            .where { Publicaciones.idUsuario eq idUser }
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