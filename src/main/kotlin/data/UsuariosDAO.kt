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
        puntosTotales = this[Usuarios.puntosTotales],
        puntosEnergia = this[Usuarios.puntosEnergia],
        rachaActual = this[Usuarios.rachaActual]
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
            it[puntosTotales] = u.puntosTotales
            it[puntosEnergia] = u.puntosEnergia
            it[rachaActual] = u.rachaActual
        } get Usuarios.id
    }

    fun actualizar(idUsuario: Int, u: UsuariosDTO): Int = transaction {
        Usuarios.update({ Usuarios.id eq idUsuario }) {
            it[nombre] = u.nombre
            it[email] = u.email
            if (u.password != null) it[password] = u.password
            it[bio] = u.bio
            it[avatar] = u.avatar
            it[puntosTotales] = u.puntosTotales
            it[puntosEnergia] = u.puntosEnergia
            it[rachaActual] = u.rachaActual
        }
    }

    fun eliminar(idUsuario: Int): Int = transaction {
        Usuarios.deleteWhere { Usuarios.id eq idUsuario }
    }
}