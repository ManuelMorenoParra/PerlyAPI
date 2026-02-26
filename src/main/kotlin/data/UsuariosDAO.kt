package edu.gva.es.data

import edu.gva.es.domain.UsuariosDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object UsuariosDAO {

    private fun ResultRow.toDTO() = UsuariosDTO(
        id = this[Usuarios.id],
        nombre = this[Usuarios.nombre],
        email = this[Usuarios.email],
        password = this[Usuarios.password],
        bio = this[Usuarios.bio],
        avatar = this[Usuarios.avatar],
        achievements = this[Usuarios.achievements], // Antes puntosTotales
        puntosEnergia = this[Usuarios.puntosEnergia],
        rachaActual = this[Usuarios.rachaActual],
        isPrivate = this[Usuarios.isPrivate], // Nuevo campo de privacidad
        onlyFollowersMessages = this[Usuarios.onlyFollowersMessages] // Nuevo campo de privacidad
    )

    fun seleccionarTodos(): List<UsuariosDTO> = transaction {
        Usuarios.selectAll().map { it.toDTO() }
    }

    fun seleccionarPorId(id: Int): UsuariosDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.id eq id }.map { it.toDTO() }.singleOrNull()
    }

    fun seleccionarPorEmail(email: String): UsuariosDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.email eq email }.map { it.toDTO() }.singleOrNull()
    }

    fun insertar(u: UsuariosDTO): Int = transaction {
        Usuarios.insert {
            it[nombre] = u.nombre
            it[email] = u.email
            it[password] = u.password ?: ""
            it[bio] = u.bio
            it[avatar] = u.avatar
            it[achievements] = u.achievements
            it[puntosEnergia] = u.puntosEnergia
            it[rachaActual] = u.rachaActual
            it[isPrivate] = u.isPrivate
            it[onlyFollowersMessages] = u.onlyFollowersMessages
        } get Usuarios.id
    }

    fun actualizar(idUsuario: Int, u: UsuariosDTO): Int = transaction {
        Usuarios.update({ Usuarios.id eq idUsuario }) {
            it[nombre] = u.nombre
            it[email] = u.email
            if (u.password != null) it[password] = u.password
            it[bio] = u.bio
            it[avatar] = u.avatar
            it[achievements] = u.achievements
            it[puntosEnergia] = u.puntosEnergia
            it[rachaActual] = u.rachaActual
            it[isPrivate] = u.isPrivate
            it[onlyFollowersMessages] = u.onlyFollowersMessages
        }
    }

    fun eliminar(idUsuario: Int): Int = transaction {
        Usuarios.deleteWhere { id eq idUsuario }
    }
}