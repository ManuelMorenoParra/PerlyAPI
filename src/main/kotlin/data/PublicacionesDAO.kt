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
        val autorId = it[Publicaciones.idUsuario] // Cogemos el ID del autor

        // 1. Buscamos el nombre y avatar del autor de este post
        val autorRow = Usuarios.select { Usuarios.id eq autorId }.singleOrNull()
        val nombreDelAutor = autorRow?.get(Usuarios.nombre) ?: "Usuario $autorId"
        val avatarDelAutor = autorRow?.get(Usuarios.avatar)

        // 2. Contar likes (Seguro)
        val totalLikes = Likes.select { Likes.idPublicacion eq pubId }.count().toInt()

        // 3. Nombres de likes
        val idsUsuarios = Likes.slice(Likes.idUsuario).select { Likes.idPublicacion eq pubId }.map { row -> row[Likes.idUsuario] }
        val usuariosLike = if (idsUsuarios.isNotEmpty()) {
            Usuarios.slice(Usuarios.nombre).select { Usuarios.id inList idsUsuarios }.map { row -> row[Usuarios.nombre] }
        } else { emptyList() }

        return PublicacionesDTO(
            id = pubId,
            idUsuario = autorId,
            texto = it[Publicaciones.texto],
            fecha = it[Publicaciones.fecha].toString(),
            imagen = it[Publicaciones.imagen],
            idRetoVinculado = it[Publicaciones.idRetoVinculado],
            likesCount = totalLikes,
            likedBy = usuariosLike,
            nombreUsuario = nombreDelAutor, // 👈 Lo enviamos a Angular
            avatarUsuario = avatarDelAutor  // 👈 Lo enviamos a Angular
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